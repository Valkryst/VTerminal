package com.valkryst.VTerminal.example.view;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public abstract class View extends JPanel {
	public abstract void modelPropertyChange(final PropertyChangeEvent event);
}
