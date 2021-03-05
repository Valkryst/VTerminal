package gui.controller;

import gui.Display;
import gui.model.Model;
import gui.view.View;
import lombok.NonNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Controller implements PropertyChangeListener {
	/** The views. */
	private final List<View> views = new ArrayList<>();
	/** The models. */
	private final List<Model> models = new ArrayList<>();

	public void addView(final @NonNull View view) {
		views.add(view);
		Display.getInstance().addView(view);
	}

	public void addView(final @NonNull View view, final Object constraints) {
		views.add(view);
		Display.getInstance().addView(view, constraints);
	}

	/**
	 * Removes a view from the controller.
	 *
	 * @param view The view to remove.
	 */
	public void removeView(final View view) {
		if (view != null) {
			views.remove(view);
			Display.getInstance().removeView(view);
		}
	}

	public void removeAllViews() {
		final var display = Display.getInstance();

		final var iterator = views.listIterator();
		while (iterator.hasNext()) {
			display.removeView(iterator.next());
			iterator.remove();
		}
	}

	/**
	 * Adds a model to the controller.
	 *
	 * @param model The model to add.
	 */
	public void addModel(final Model model) {
		if (model != null) {
			models.add(model);
			model.addPropertyChangeListener(this);
			model.fireInitialProperties();
		}
	}

	/**
	 * Removes a model from the controller.
	 *
	 * @param model The model to remove.
	 */
	public void removeModel(final Model model) {
		if (model != null) {
			models.remove(model);
			model.removePropertyChangeListener(this);
		}
	}

	public void removeAllModels() {


		final var iterator = models.listIterator();
		while (iterator.hasNext()) {
			iterator.next().removePropertyChangeListener(this);
			iterator.remove();
		}
	}

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		for (final var view : views) {
			view.modelPropertyChange(event);
		}
	}

	/**
	 * This is a convenience method that subclasses can call upon to fire
	 * property changes back to the models. This method uses reflection to
	 * inspect each of the model classes to determine whether it's the owner of
	 * the property in question.
	 *
	 * @param propertyName Name of the property.
	 *
	 * @param newValue An object that represents the property's new value.
	 */
	protected void setModelProperty(final String propertyName, final Object newValue) {
		for (final var model : models) {
			try {
				final var methodName = "set" + propertyName;
				final var method = model.getClass().getMethod(methodName, newValue.getClass());

				method.invoke(model, newValue);
			} catch (final NoSuchMethodException e) {
				/*
				 * We're iterating over the models, trying to find the model
				 * that has the 'setPropertyName' method, so this could be
				 * thrown a few times.
				 *
				 * Because this is intended behaviour, we display the exception
				 * and then ignore it.
				 */
				final var stackTraceElements = e.getStackTrace();
				for (final var element : stackTraceElements) {
					final var className = element.getClassName();
					final var methodName = element.getMethodName();
					final var fileName = element.getFileName();
					final var lineNumber = element.getLineNumber();
					System.err.println("\tat " + className + "." + methodName + "(" + fileName + ":" + lineNumber + ")");
				}
			} catch (final IllegalAccessException e) {
				/*
				 * If we found the model with the 'setPropertyName' method,
				 * this can be thrown if the model is enforcing the Java
				 * language access control and the 'setPropertyName' method is
				 * inaccessible.
				 */
				e.printStackTrace();
			} catch (final InvocationTargetException e) {
				/*
				 * If we found the model with the 'setPropertyName' method, this
				 * can be thrown if the 'setPropertyName' method throws an
				 * exception.
				 */
				e.printStackTrace();
			}
		}
	}
}
