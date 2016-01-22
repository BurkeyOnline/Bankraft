package com.cjburkey.plugin.bankraft.io;

import java.io.File;
import com.cjburkey.plugin.bankraft.Bankraft;

public class IO {
	
	public static final File getDataDir() {
		File dir = new File(Bankraft.getPlugin().getDataFolder(), "/data/");
		return dir;
	}
	
}