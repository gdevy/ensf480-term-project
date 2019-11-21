package client.userInterface;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class LoginPanel extends JPanel {
	private JTextField txtUsername;
	private JTextField txtPassword;

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		setLayout(new MigLayout("", "[][][][][][]", "[][][][][]"));
		setLayout(new MigLayout("", "[1px][][][][][][][103.00,grow]", "[1px][][][][][][][]"));
		
		JLabel lblUsername = new JLabel("Username:");
		add(lblUsername, "cell 6 5,alignx trailing,growy");
		
		txtUsername = new JTextField();
		add(txtUsername, "cell 7 5,alignx left");
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		add(lblPassword, "cell 6 6,alignx trailing");
		
		txtPassword = new JTextField();
		add(txtPassword, "cell 7 6,alignx left");
		txtPassword.setColumns(10);
		
		JButton btnSignIn = new JButton("Sign In");
		add(btnSignIn, "cell 7 7");

	}

}
