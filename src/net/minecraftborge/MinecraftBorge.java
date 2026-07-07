package net.minecraftborge;

import net.minecraft.client.Minecraft;
import net.minecraftborge.loader.GameObfuscationType;
import net.minecraftborge.loader.ModList;
import net.minecraftborge.loader.ModloadingException;
import net.minecraftborge.loader.asm.ASMClassHelper;
import net.minecraftborge.loader.asm.ASMDataTable;
import net.minecraftborge.loader.asm.mixin.Mixinjector;
import net.minecraftborge.loader.event.IModLifecycleListener;
import net.minecraftborge.loader.event.lifecycle.ModPreInitializationEvent;
import net.minecraftborge.loader.loading.ModCandidate;
import net.minecraftborge.loader.loading.ModClassLoader;
import net.minecraftborge.loader.loading.ModLoadingClassVisitor;
import org.objectweb.asm.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

public class MinecraftBorge {
	public static final boolean EXPERIMENTAL_APPLET_LOADPOINT = true;

	public static final String UPDATE_URL = "https://minecraftborge.net/files/versions.xml";
	public static final String FALLBACK_UPDATE_URL = "https://minecraft-borge.github.io/files/versions.xml";
	private static boolean useFallback = false;
	public static boolean hasLoaded = false;

	public static final String VERSION = "1.6.4";
	public static final String MC_VERSION = "b1.7.3";
	public static String NEW_VERSION = null;

	public static final Map<String, Object> blackboard = Collections.synchronizedMap(new HashMap<>());

	private static File modsDir;
	public static File getModsDir() {
		if (modsDir == null) {
			modsDir = new File(Minecraft.getMinecraftDir(), "mods");
			if (!modsDir.isDirectory() && !modsDir.mkdirs()) throw new RuntimeException("Could not create mods folder");
		}
		return modsDir;
	}

	private static ClassLoader classLoader = MinecraftBorge.class.getClassLoader();
	public static ClassLoader getClassLoader() {
		return classLoader;
	}
	public static InputStream getResourceAsStream(String name) {
		return getClassLoader().getResourceAsStream(name);
	}
	public static URL getResource(String name) {
		return getClassLoader().getResource(name);
	}

	public static void scanAndLoadMods() throws ModloadingException {
		blackboard.put("BorgeVersion", VERSION);
		blackboard.put("MinecraftVersion", MC_VERSION);

		System.out.println("Environment obfuscation: " + GameObfuscationType.detect());

		loadBorgeConfig();

		new Thread(MinecraftBorge::connectWithSite, "BorgeUpdateChecker").start();

		File[] candidates = getModsDir().listFiles((file) -> file.isFile() && file.getName().endsWith(".jar"));

		if (candidates != null) {
			System.out.println(candidates.length + " mod candidates");

			List<ModCandidate> mods = new ArrayList<>();
			for (File file : candidates) {
				System.out.println("Checking " + file.getName());

				JarFile jar = null;
				try {
					jar = new JarFile(file);
					JarEntry manifestEntry = jar.getJarEntry("META-INF/MANIFEST.MF");
					if (manifestEntry == null) {
						System.out.println("Manifest missing");
						continue;
					}
					final Manifest manifest;
					try (InputStream in = jar.getInputStream(manifestEntry)) {
						manifest = new Manifest(in);
					} catch (Throwable e) {
						throw new IllegalStateException("Exception reading manifest", e);
					}
					Attributes attributes = manifest.getMainAttributes();
					ModCandidate candidate = new ModCandidate(jar, file, attributes);
					System.out.println(candidate.attributes.keySet());
					System.out.println(candidate.attributes.values());
					if (candidate.validateProperties()) mods.add(candidate);
				} catch (Throwable e) {
					e.printStackTrace(System.err);
					try {
						if (jar != null) {
							jar.close();
						}
					} catch (Throwable ignored) {}
				}
			}

			localload:
			if (GameObfuscationType.detect() == GameObfuscationType.NONE) {
				System.out.println("No obfuscation, dev env is assumed");
				try {
					final Manifest manifest;
					try (InputStream in = getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF")) {
						if (in == null) {
							System.err.println("No local manifest, disregarding");
							break localload;
						}
						manifest = new Manifest(in);
					} catch (IOException e) {
						throw new IllegalStateException("Exception reading manifest", e);
					}

					Attributes attributes = manifest.getMainAttributes();
					ModCandidate candidate = new ModCandidate(null, null, attributes);
					System.out.println(candidate.attributes.keySet());
					System.out.println(candidate.attributes.values());
					if (candidate.validateProperties()) mods.add(candidate);
				} catch (Throwable e) {
					e.printStackTrace(System.err);
				}
			}

			Map<String, ModCandidate> candidatesByName = mods.stream().collect(Collectors.toMap(c -> c.name, c -> c));

			List<String> modIds = new ArrayList<>();
			Map<String, URL> nameToUrl = new HashMap<>();
			for (ModCandidate candidate : mods) {
				if (modIds.contains(candidate.name)) throw new ModloadingException("Duplicate mod ID: " + candidate.name);
				modIds.add(candidate.name);
				try {
					if (candidate.sourceFile != null) nameToUrl.put(candidate.name, candidate.sourceFile.toURI().toURL());
				} catch (Throwable e) {
					throw new ModloadingException(e);
				}
			}

			final List<String> sortedMods = getSortedModIDs(mods);

			ModClassLoader cl = new ModClassLoader(sortedMods.stream().map(nameToUrl::get).toArray(URL[]::new), MinecraftBorge.class.getClassLoader());
			Thread.currentThread().setContextClassLoader(cl);
			classLoader = cl;

			System.out.println("Collecting ASM data");
			ASMDataTable table = new ASMDataTable();
			ModLoadingClassVisitor cv = new ModLoadingClassVisitor(Opcodes.ASM8, table);
			for (JarFile jar : mods.stream().map(candidate -> candidate.jar).collect(Collectors.toList())) {
				if (jar == null) {
					try (
						InputStream in = Objects.requireNonNull(getClassLoader().getResourceAsStream("runtime.txt"));
						BufferedReader reader = new BufferedReader(new InputStreamReader(in))
					) {
						reader.lines()
								.map(String::trim)
								.filter(s -> !s.isEmpty())
								.filter(s -> !s.startsWith("#"))
								.forEach(line -> {
									try (InputStream stream = getClassLoader().getResourceAsStream(line + ".class")) {
										assert stream != null;
										ClassReader cr = new ClassReader(stream);
										cr.accept(cv, 0);
									} catch (Throwable e) {
										System.err.println("Invalid class name " + line + ": " + e);
									}
								});
					} catch (Throwable e) {
						System.err.println("runtime.txt not found, annotations won't be processed!");
					}
					continue;
				}
				Iterator<JarEntry> stream = jar.stream().iterator();
				while (stream.hasNext()) {
					JarEntry entry = stream.next();
					String name = entry.getName();
					if (name.endsWith(".class")) {
						InputStream in = getClassLoader().getResourceAsStream(name);
						if (in != null) {
							try {
								ClassReader cr = new ClassReader(in);
								cr.accept(cv, 0);
								in.close();
							} catch (Throwable e) {
								throw new ModloadingException(e);
							}
						} else throw new NullPointerException("Class data " + name + " returns null stream");
					}
				}
			}

			//table.printDebug();
			System.out.println("done!");

			try {
				ASMClassHelper.generateASMEventBus((ModClassLoader) getClassLoader(), table);
			} catch (Exception e) {
				throw e instanceof ModloadingException ? (ModloadingException) e : new ModloadingException(e);
			}

			Mixinjector.applyMixins((ModClassLoader) getClassLoader(), table, sortedMods);

			List<IModLifecycleListener> sortedListeners = new ArrayList<>();
			for (String modid : sortedMods) {
				ModCandidate candidate = candidatesByName.get(modid);
				String className = candidate.attributes.getValue("Mod-Lifecycle-Listener");
				IModLifecycleListener listener = null;
				if (className != null) {
					try {
						Class<?> clazz = getClassLoader().loadClass(className);
						listener = (IModLifecycleListener) clazz.getConstructor().newInstance();
					} catch (Throwable e) {
						e.printStackTrace(System.err);
					}
				}
				sortedListeners.add(listener);

				if (listener != null) {
					String updateXML = candidate.attributes.getValue("Mod-Update-URL");
					if (updateXML != null) {
						IModLifecycleListener lifecycle = listener;
						new Thread(() -> {
							try {
								System.out.println("Attempting to retrieve mod versions for " + modid + " from " + updateXML);
								URL url = new URL(updateXML);
								DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
								DocumentBuilder builder = factory.newDocumentBuilder();
								Document document = builder.parse(url.openStream());
								Element root = document.getDocumentElement();

								Node client = root.getElementsByTagName("Client").item(0);
								if (client != null && client.getNodeType() == Node.ELEMENT_NODE) {
									NodeList versions = ((Element)client).getElementsByTagName("version");
									for (int i = 0; i < versions.getLength(); i++) {
										Node version = versions.item(i);
										if (version.getNodeType() == Node.ELEMENT_NODE) {
											Element element = (Element) version;
											String mcVersion = element.getAttribute("minecraft");
											String modVersion = element.getTextContent();

											if (MC_VERSION.equals(mcVersion)) {
												System.out.println("Found online version for " + modid + ": " + modVersion);
												lifecycle.newestVersionOnline(modVersion);
												break;
											}
										}
									}
								}
							} catch (Throwable e) {
								System.out.println("Could not retrieve mod versions for " + modid + ": " + e);
							}
						}, modid + " update checker thread").start();
					}
				}
			}

			ModList.initialize(sortedMods, candidatesByName, sortedListeners);
			ModList.get().modPreInit(new ModPreInitializationEvent(table));
		} else throw new ModloadingException("Candidates array is null");
	}

	private static List<String> getSortedModIDs(List<ModCandidate> mods) throws ModloadingException {
		List<ModCandidate> toSort = new ArrayList<>(mods);
		List<String> sortedMods = new ArrayList<>();
		int modcounter = 0;
		int modcounterOld = -1;
		while (!toSort.isEmpty()) {
			if (modcounterOld == modcounter) throw new ModloadingException("Dependency error");
			modcounterOld = modcounter;
			loop:
			for (int i = toSort.size()-1; i >= 0; i--) {
				ModCandidate candidate = toSort.get(i);
				for (String dependency : candidate.dependencies) {
					if (!sortedMods.contains(dependency)) continue loop;
				}
				modcounter++;
				sortedMods.add(candidate.name);
				toSort.remove(candidate);
			}
		}
		return sortedMods;
	}

	private static void loadBorgeConfig() {
		try {
			File file = new File("BorgeConfig.txt");
			if (!file.isFile()) {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.write("ModifyMainMenu=true\n");
				writer.write("PersistentBlockUpdates=true\n");
				writer.write("FixFurnaceFuelConsumption=true\n");
				writer.write("FixAmbientOcclusion=true\n");
				writer.close();
			}
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String next = scanner.nextLine().trim();
				String[] split = next.split("=", 2);
				if (!next.startsWith("#") && split.length == 2) {
					String key = split[0];
					String value = split[1];

					switch (key) {
						case "ModifyMainMenu":
						case "PersistentBlockUpdates":
						case "FixFurnaceFuelConsumption":
						case "FixAmbientOcclusion":
							blackboard.put(key, "true".equalsIgnoreCase(value));
							break;
						default:
							System.out.println("Unknown borge option: " + key);
							break;
					}
				}
			}
			scanner.close();
		} catch (IOException e) {
			System.err.println("Could not load Borge config!");
			blackboard.put("ModifyMainMenu", true);
		}
	}

	private static InputStream getXMLStream() {
		if (!useFallback) {
			try {
				URL url = new URL(UPDATE_URL);
				return Objects.requireNonNull(url.openStream());
			} catch (Throwable e) {
				System.err.println("Could not open stream with main update URL: " + e);
			}
		}
		useFallback = true;
		try {
			URL url = new URL(FALLBACK_UPDATE_URL);
			return Objects.requireNonNull(url.openStream());
		} catch (Throwable e) {
			System.err.println("Could not open stream with fallback update URL: " + e);
		}
		return null;
	}
	private static void connectWithSite() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			try (InputStream in = getXMLStream();
			     Scanner scanner = new Scanner(new BufferedInputStream(in))
			) {
				StringBuilder source = new StringBuilder().append("Update XML:").append("\n\n");
				while (scanner.hasNextLine()) {
					source.append(scanner.nextLine()).append("\n");
				}
				System.out.print(source.append("\n"));
			}
			Document document = builder.parse(getXMLStream());
			Element root = document.getDocumentElement();

			Node client = root.getElementsByTagName("Client").item(0);
			if (client != null && client.getNodeType() == Node.ELEMENT_NODE) {
				NodeList versions = ((Element)client).getElementsByTagName("version");
				for (int i = 0; i < versions.getLength(); i++) {
					Node version = versions.item(i);
					if (version.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) version;
						String mcVersion = element.getAttribute("minecraft");
						String borgeVersion = element.getTextContent();

						if (MC_VERSION.equals(mcVersion)) {
							blackboard.put("BorgeVersionLatest", borgeVersion);

							if (!VERSION.equals(borgeVersion)) {
								System.out.println("New borge version available: " + borgeVersion);
								NEW_VERSION = borgeVersion;
							}
						}
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace(System.err);
		}
	}

	public static String formatClassName(Class<?> clazz) {
		String str = clazz.getSimpleName();
		Class<?> enclosing = clazz;
		while ((enclosing = enclosing.getEnclosingClass()) != null) {
			str = enclosing.getSimpleName() + "." + str;
		}
		return str;
	}
}
