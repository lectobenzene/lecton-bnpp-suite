package corecode;

import java.util.ArrayList;

import corecode.MultiLangController;

public class MultiLangParser {

	public static final String SOURCE_FILE = "/Users/Saravana/Downloads/multilang/de-BE.tra";
	public static final String DESTINATION_FILE = "/Users/Saravana/Downloads/multilang/strings.xml";

	public static void main(String[] args) {
		
		// Items to be underlined
		ArrayList<String> underlinedArrayList = new ArrayList<String>();
		underlinedArrayList.add("53493");
		underlinedArrayList.add("53491");
		underlinedArrayList.add("51324");
		underlinedArrayList.add("51335");

		MultiLangController controller = new MultiLangController("x-MacRoman","UTF-8");
		System.out.println("Encoding is "+MultiLangController.encodingRead);
		System.out.println("Encoding is "+MultiLangController.encodingWrite);
		controller.updateMultiLang(SOURCE_FILE, DESTINATION_FILE, underlinedArrayList);
	}
}
