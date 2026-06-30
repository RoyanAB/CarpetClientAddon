package cn.royan.carpetclientaddon.chunkdebugtool.deobfuscator.mapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Copy from Carpet-TIS-Addition
 */

public interface MappingReader {
	Map<String, String> readMapping(BufferedReader mappingReader) throws IOException;
}
