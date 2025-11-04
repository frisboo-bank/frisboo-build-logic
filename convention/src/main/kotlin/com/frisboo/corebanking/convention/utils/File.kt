package com.frisboo.corebanking.convention.utils

import org.gradle.api.file.Directory
import java.net.URL

public fun URL.copyWithRecurtion(destination: Directory) {
    val source = this
    val destRoot = destination.asFile.toPath()

    when (source.protocol) {
        "file" -> {
            val sourceRoot = java.nio.file.Paths.get(source.toURI())
            java.nio.file.Files.walk(sourceRoot).use { paths ->
                paths.filter { java.nio.file.Files.isRegularFile(it) }.forEach { p ->
                    val target = destRoot.resolve(sourceRoot.relativize(p).toString())
                    java.nio.file.Files.createDirectories(target.parent)
                    java.nio.file.Files.copy(p, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING)
                }
            }
        }

        "jar" -> {
            val rawPath = source.path
            val fileUri = java.net.URI.create(rawPath.substringBefore("!"))
            val jarUri = java.net.URI.create("jar:$fileUri")
            val entryPath = rawPath.substringAfter("!/")
            val fs = try {
                java.nio.file.FileSystems.newFileSystem(jarUri, emptyMap<String, Any>())
            } catch (_: java.nio.file.FileSystemAlreadyExistsException) {
                java.nio.file.FileSystems.getFileSystem(jarUri)
            }
            fs.use { zfs ->
                val sourceRoot = zfs.getPath("/").resolve(entryPath)
                java.nio.file.Files.walk(sourceRoot).use { paths ->
                    paths.filter { java.nio.file.Files.isRegularFile(it) }.forEach { p ->
                        val target = destRoot.resolve(sourceRoot.relativize(p).toString())
                        java.nio.file.Files.createDirectories(target.parent)
                        java.nio.file.Files.copy(p, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING)
                    }
                }
            }
        }

        else -> error("Unsupported protocol: ${source.protocol}")
    }
}
