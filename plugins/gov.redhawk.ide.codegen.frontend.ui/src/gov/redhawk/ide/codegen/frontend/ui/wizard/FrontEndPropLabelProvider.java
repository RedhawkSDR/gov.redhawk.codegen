package gov.redhawk.ide.codegen.frontend.ui.wizard;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class FrontEndPropLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		FrontEndProp feiProp = (FrontEndProp) element;
		switch (columnIndex) {
		case 0:
			return feiProp.getProp().getName();
		case 1:
			return feiProp.getProp().getType().getName();
		case 2:
			return Boolean.toString(feiProp.isRequired());
		case 3:
			return feiProp.getProp().getDescription();
		default:
			return "";
		}
	}

}
