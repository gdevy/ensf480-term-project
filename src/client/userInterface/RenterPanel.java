package client.userInterface;

import javax.swing.JPanel;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import net.miginfocom.swing.MigLayout;

public class RenterPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public RenterPanel() {
		setLayout(new MigLayout("", "[][grow]", "[][grow]"));
	}

}
