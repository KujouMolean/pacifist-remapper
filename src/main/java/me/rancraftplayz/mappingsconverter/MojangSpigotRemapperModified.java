//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.rancraftplayz.mappingsconverter;

import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class MojangSpigotRemapperModified extends MojangSpigotRemapper {
    public static void remapAll(Path input, File mappingsDir, String mcVersion, List<Path> libraries, @Nullable Path accessWidener, @Nullable List<Path> accessWidnerLibs) throws IOException {
        File mappings = proguardCsrgTiny(mcVersion, mappingsDir);

        remap(input, mappings, libraries);

        if (accessWidener != null) {
            if (accessWidnerLibs == null) {
                accessWidnerLibs = new ArrayList();
            }

            remapAccessWidener(input, accessWidener, mappings.toPath(), (List) accessWidnerLibs);
        }

    }

    public static void addFileToZip(File source, File file, String name) {
        try {
            File tmpZip = File.createTempFile(source.getName(), (String) null);
            tmpZip.delete();
            if (!source.renameTo(tmpZip)) {
                throw new Exception("Could not make temp file (" + source.getName() + ")");
            }

            byte[] buffer = new byte[1024];
            ZipInputStream zin = new ZipInputStream(new FileInputStream(tmpZip));
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(source));


            InputStream in = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(name));

            for (int read = in.read(buffer); read > -1; read = in.read(buffer)) {
                out.write(buffer, 0, read);
            }

            out.closeEntry();
            in.close();

            for (ZipEntry ze = zin.getNextEntry(); ze != null; ze = zin.getNextEntry()) {
                if (ze.getName().equals(name)) {
                    continue;
                }
                out.putNextEntry(ze);

                for (int read = zin.read(buffer); read > -1; read = zin.read(buffer)) {
                    out.write(buffer, 0, read);
                }

                out.closeEntry();
            }

            out.close();
            tmpZip.delete();
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }

    public static void remapAccessWidener(Path inputJar, Path input, Path mappings, List<Path> libs) throws IOException {
        File output = Paths.get(input.toFile().getParent() + "/" + FilenameUtils.removeExtension(input.toFile().getName()) + "-obf.accesswidener").toFile();
        if (output.exists()) {
            output.delete();
        }

        File accessWidener = (new RemapAccessWidener(input.toFile(), output)).remap(mappings.toFile(), libs, true).outputFile;
        addFileToZip(inputJar.toFile(), accessWidener, input.toFile().getName());
        output.delete();
    }
}
