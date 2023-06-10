package seliniumcommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;

public class TableUtility {
	public static List<ArrayList<String>> get_Dynamic_TwoDimension_TablElemnts(List<WebElement> rowItems,
			List<WebElement> columnItems) {
		int rSize = rowItems.size() - 1;
		int colnmSize = columnItems.size();
		String[] columnList = new String[colnmSize / rSize];
        List<ArrayList<String>> gridData = new ArrayList<ArrayList<String>>();
		int x = 0; // Controlling the cell values
		for (int i = 0; i < rSize; i++) { // Iterating each row
			for (int j = 0; j < columnList.length; j++) { // Iterating each array
				columnList[j] = columnItems.get(x).getText();
				x++;
			}
			gridData.add(new ArrayList<String>(Arrays.asList(columnList))); // Converting array to array list
		}
		return gridData;
	}

	public static List<ArrayList<String>> get_Dynamic_TwoDimension_ObscuraTablElemnts(List<WebElement> rowItems,
			List<WebElement> columnItems) {
		int rSize = rowItems.size() - 2;
		int colnmSize = columnItems.size();
		String[] columnList = new String[colnmSize / rSize];
        List<ArrayList<String>> gridData = new ArrayList<ArrayList<String>>();
		int x = 0; // Controlling the cell values
		for (int i = 0; i < rSize; i++) { // Iterating each row
			for (int j = 0; j < columnList.length; j++) { // Iterating each array
				columnList[j] = columnItems.get(x).getText();
				x++;
			}
			gridData.add(new ArrayList<String>(Arrays.asList(columnList))); // Converting array to array list
		}
		return gridData;
	}

}
