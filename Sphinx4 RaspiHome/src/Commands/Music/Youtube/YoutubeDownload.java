package Commands.Music.Youtube;

import java.io.File;

public class YoutubeDownload implements Runnable {
	
	static String url;
	static String name;
	
	public YoutubeDownload(String link, String nam) {
		url = link;
		name = nam;
    }

	@Override
	public void run() {
		try {
			File out = new File(new java.io.File( "." ).getCanonicalPath() + File.separator+ "music" + File.separator);
			out.mkdir();
			String[] cmd = {"youtube-dl", "--no-check-certificate", url, out.getPath()+"temp.webm"};
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            String[] cmd2 = {"ffmpreg", "-i", out.getPath() + "temp.webm", "-vn", "-ab 128k", "-ar 44100", "-y", out.getPath() + "name.mp3"};
            p = Runtime.getRuntime().exec(cmd2);
            p.waitFor();
        } catch (Exception e) {e.printStackTrace();}	
	}
}
