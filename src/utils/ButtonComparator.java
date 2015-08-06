package utils;

import java.util.Comparator;

public class ButtonComparator implements Comparator<SimpleButton>{
	

	@Override
	public int compare(SimpleButton b1, SimpleButton b2) {
		return b1.getText().compareTo(b2.getText());
	}

}
