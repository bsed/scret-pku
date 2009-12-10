package easyJ.common;
/**
 * 这个类用来取得系统的一些配置，例如linux系统，还是windows系统，
 * 配置文件的路径，以及需要访问文件的路径，在linux下和windows下是不同的，
 * 等。
 * @author liufeng
 *
 */
public class SystemEnviroment {
	public static final String javaVersion;
	public static final String javaVendor;
	public static final String javaVendorUrl;
	public static final String javaHome;
	public static final String javaVmSpecificationVersion;
	public static final String javaVmSpecificationVendor;
	public static final String javaVmSpecificationName;
	public static final String javaVmVersion;
	public static final String javaVmVendor;
	public static final String javaVmName;
	public static final String javaSpecificationVersion;
	public static final String javaSpecificationVendor;
	public static final String javaSpecificationName;
	public static final String javaClassVersion;
	public static final String javaClassPath;
	public static final String javaLibraryPath;
	public static final String javaIoTmpdir;
	public static final String javaCompiler;
	public static final String javaExtDirs;
	public static final String osName;
	public static final String osArch;
	public static final String osVersion;
	public static final String fileSeparator;
	public static final String pathSeparator;
	public static final String lineSeparator;
	public static final String userName; 
	public static final String userHome;
	public static final String userDir;
	
	static {
		javaVersion = System.getProperty("java.version");
		javaVendor = System.getProperty("java.vendor");
		 javaVendorUrl = System.getProperty("java.vendor.url");
		 javaHome = System.getProperty("java.home");
		 javaVmSpecificationVersion = System.getProperty("java.vm.specification.version");
		 javaVmSpecificationVendor = System.getProperty("java.vm.specification.vendor");
		 javaVmSpecificationName = System.getProperty("java.vm.specification.name");
		 javaVmVersion = System.getProperty("java.vm.version");
		 javaVmVendor = System.getProperty("java.vm.vendor");
		 javaVmName = System.getProperty("java.vm.name");
		 javaSpecificationVersion = System.getProperty("java.specification.version");
		 javaSpecificationVendor = System.getProperty("java.specification.vendor");
		 javaSpecificationName = System.getProperty("java.specification.name");
		 javaClassVersion = System.getProperty("java.class.version");
		 javaClassPath = System.getProperty("java.class.path");
		 javaLibraryPath = System.getProperty("java.library.path");
		 javaIoTmpdir = System.getProperty("java.io.tmpdir");
		 javaCompiler = System.getProperty("java.compiler");
		 javaExtDirs = System.getProperty("java.ext.dirs");
		 osName = System.getProperty("os.name");
		 osArch = System.getProperty("os.arch");
		 osVersion = System.getProperty("os.version");
		 fileSeparator = System.getProperty("file.separator");
		 pathSeparator = System.getProperty("path.separator");
		 lineSeparator = System.getProperty("line.separator");
		 userName = System.getProperty("user.name "); 
		 userHome = System.getProperty("user.home");
		 userDir = System.getProperty("user.dir");
	}

}
