package org.jboss.tools.aerogear.hybrid.core.internal.util;

/**
 * Copied from org.eclipse.ui.internal.wizards.datatransfer.TarEntry.
 */
public class TarEntry implements Cloneable {
	private String name;
	private long mode, time, size;
	private int type;
	int filepos;

	/**
	 * Entry type for normal files.
	 */
	public static final int FILE = '0';

	/**
	 * Entry type for directories.
	 */
	public static final int DIRECTORY = '5';

	/**
	 * Create a new TarEntry for a file of the given name at the
	 * given position in the file.
	 * 
	 * @param name filename
	 * @param pos position in the file in bytes
	 */
	TarEntry(String name, int pos) {
		this.name = name;
		mode = 0644;
		type = FILE;
		filepos = pos;
		time = System.currentTimeMillis() / 1000;
	}

	/**
	 * Returns the type of this file, one of FILE, LINK, SYM_LINK,
	 * CHAR_DEVICE, BLOCK_DEVICE, DIRECTORY or FIFO.
	 * 
	 * @return file type
	 */
	public int getFileType() {
		return type;
	}

	/**
	 * Returns the mode of the file in UNIX permissions format.
	 * 
	 * @return file mode
	 */
	public long getMode() {
		return mode;
	}

	/**
	 * Returns the name of the file.
	 * 
	 * @return filename
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the size of the file in bytes.
	 * 
	 * @return filesize
	 */
	public long getSize() {
		return size;
	}

	/**
	 * Returns the modification time of the file in seconds since January
	 * 1st 1970.
	 * 
	 * @return time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Sets the type of the file, one of FILE, LINK, SYMLINK, CHAR_DEVICE,
	 * BLOCK_DEVICE, or DIRECTORY.
	 * 
	 * @param type
	 */
	public void setFileType(int type) {
		this.type = type;
	}

	/**
	 * Sets the mode of the file in UNIX permissions format.
	 * 
	 * @param mode
	 */
	public void setMode(long mode) {
		this.mode = mode;
	}

	/**
	 * Sets the size of the file in bytes.
	 * 
	 * @param size
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * Sets the modification time of the file in seconds since January
	 * 1st 1970.
	 * 
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}
}