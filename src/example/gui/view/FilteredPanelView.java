package gui.view;

import com.jhlabs.image.FlipFilter;
import com.jhlabs.image.GaussianFilter;
import com.valkryst.VTerminal.component.VPanel;
import com.valkryst.VTerminal.image.SequentialOp;

import java.beans.PropertyChangeEvent;
import java.util.concurrent.ThreadLocalRandom;

public class FilteredPanelView extends View {
	public FilteredPanelView() {
		final var component = new VPanel(64, 32);

		var sequentialOp = new SequentialOp(
			new GaussianFilter(),
			new FlipFilter(FlipFilter.FLIP_H),
			new FlipFilter(FlipFilter.FLIP_V)
		);

		for (int y = 0 ; y < component.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < component.getWidthInTiles() ; x++) {
				component.setCodePointAt(x, y, ThreadLocalRandom.current().nextInt(1000));
				component.setSequentialImageOpAt(x, y, sequentialOp);
			}
		}

		this.add(component);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}
}
