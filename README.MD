#### License: 

Use this code however you wish. Modifications, improvements, additional lists of names, and new algorithms are welcome.

Please refer to the LICENSE file for additional information.

---

# AsciiPanel


AsciiPanel simulates a Unicode terminal display. It supports arbitrary fore/background colors,  terminal sizes, and allows
the use of any mono-space font.

This project is currently under active development and code-breaking changes are being committed as development progresses
and code is refactored. This readme file will be updated to note when the project is in a usable state.

## How to Setup (Module):

1. Clone the project from GitHub.
2. Open your project in IntelliJ.
3. Open the *Project Structure* menu. (S + ALT + CTRL + SHIFT)
4. Enter the *Modules* subsection.
5. Click the green "+" at the top of the window.
6. Select *Import Module*.
7. Navigate to the cloned project and double-click on *pom.xml*.
8. Click on the name of your project in the *Modules* subsection.
9. Enter the *Dependencies* tab of your project.
10. Click on the green "+" at the right-edge of the window.
11. Click *Module Dependency...*.
12. Select *AsciiPanel*.

You can now use AsciiPanel in your project.

## How to Setup (JavaFX Application):

```java
    import javafx.application.Application;
    import javafx.stage.Stage;

    import java.io.IOException;

    public class Driver extends Application {
        public static void main(String[] args) throws IOException {
            launch();
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            try {
                final AsciiFont font = new AsciiFont("Fonts/Lucida Sans Unicode/lsansuni.ttf", 16);

                final AsciiPanel panel = new AsciiPanel(100, 50, font);
                panel.setFocusTraversable(true);

                final Group root = new Group();
                root.getChildren().add(terminal);
                
                final Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                // GAME LOOP GOES HERE
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```

## Notes:

### Fonts:
AsciiPanel supports the customization of fonts. The following mono-space fonts are provided:

* [DejaVu](http://dejavu-fonts.org/wiki/Main_Page):
    * DejaVuSansMono.ttf
    * DejaVuSansMono-Bold.ttf
    * DejaVuSansMono-BoldOblique.ttf
    * DejaVuSansMono-Oblique.ttf
* [GNU FreeFont](https://www.gnu.org/software/freefont/):
    * FreeMono.ttf
    * FreeMonoBold.ttf
    * FreeMonoBoldOblique.ttf
    * FreeMonoOblique.ttf
    
No font will look correct at all font sizes. An example of this can be seen below where *DejaVuSansMono.ttf* is used with
a font size of 17 in the first image and a font size of 20 in the second. You will need to experiment and decide on a font
size to use for your project.

<center>
| Font Size 17 | Font Size 20 |
| ------------ | ------------ |
| ![Example of font spacing issue at 17pt.](https://github.com/Valkryst/AsciiPanel/blob/master/images/Font-Size%20Spacing%20(17pt).png) | ![Example of font spacing issue at 20pt.](https://github.com/Valkryst/AsciiPanel/blob/master/images/Font-Size%20Spacing%20(20pt).png) |
</center>
    
### Misc:

* Calling the *draw()* function of the *AsciiPanel* will draw the stored arrays of *AsciiStrings* onto the *AsciiPanel*.
   After calling *draw()*, the screen will remain as it was last drawn until the next call to *draw()* is made.
   
   You do not need to continuously call *draw()* to keep an image on the *AsciiPanel*.
   
* The use of non-mono-space fonts is not supported, but it not specifically prevented.