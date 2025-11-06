/*
 * Copyright 2025 Frisboo Bank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.frisboo.corebanking.coreconvention.utils

import org.gradle.api.file.Directory
import java.net.URI
import java.net.URL
import java.nio.file.FileSystemAlreadyExistsException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

public fun URL.copyWithRecursion(destination: Directory) {
    val source = this
    val destRoot = destination.asFile.toPath()

    when (source.protocol) {
        "file" -> {
            val sourceRoot =
                Paths
                    .get(source.toURI())
            Files.walk(sourceRoot).use { paths ->
                paths
                    .filter {
                        Files
                            .isRegularFile(it)
                    }.forEach { p ->
                        val target = destRoot.resolve(sourceRoot.relativize(p).toString())
                        Files
                            .createDirectories(target.parent)
                        Files
                            .copy(p, target, StandardCopyOption.REPLACE_EXISTING)
                    }
            }
        }

        "jar" -> {
            val rawPath = source.path
            val fileUri = URI.create(rawPath.substringBefore("!"))
            val jarUri = URI.create("jar:$fileUri")
            val entryPath = rawPath.substringAfter("!/")
            val fs =
                try {
                    FileSystems
                        .newFileSystem(jarUri, emptyMap<String, Any>())
                } catch (_: FileSystemAlreadyExistsException) {
                    FileSystems
                        .getFileSystem(jarUri)
                }
            fs.use { zfs ->
                val sourceRoot = zfs.getPath("/").resolve(entryPath)
                Files.walk(sourceRoot).use { paths ->
                    paths
                        .filter {
                            Files
                                .isRegularFile(it)
                        }.forEach { p ->
                            val target = destRoot.resolve(sourceRoot.relativize(p).toString())
                            Files
                                .createDirectories(target.parent)
                            Files
                                .copy(p, target, StandardCopyOption.REPLACE_EXISTING)
                        }
                }
            }
        }

        else -> error("Unsupported protocol: ${source.protocol}")
    }
}
