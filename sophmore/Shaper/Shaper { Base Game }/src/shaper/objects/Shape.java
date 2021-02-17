/*
 * Code written by
 * Eddie Federmeyer
 *  - Hi
 */
package shaper.objects;

/**
 *
 * @author Eddie Federmeyer <eddiefed.com>
 */
public class Shape {
    private String shape;       // TRIANGLE, DIAMOND, SQUARE
    private String effect;      // {R} - Reverse, {SW} - Switch, {S} - Steal, {RAND} - Randomize color, {GF} - Go to front, {GE} - Go to end, {GM} - Go to middle, {NULL} - Nothing
    private String color;       // RED, BLUE, GREEN
    private String path;        // File path to image
    
    
    /** Creates a new Shape
     *
     * @param shape The object shape
     * @param effect the object effect
     * @param color  The object color
     */
    public Shape(String shape, String effect, String color) {
        this.shape = shape;
        this.effect = effect;
        this.color = color;
        
        this.path = this.shape + "-" + this.color;
    }
    
    
    /** Gets the shape
     *
     * @return {TRIANGLE, DIAMOND, SQUARE}
     */
    public String getShape() {
        return this.shape;
    }

    
    /** Gets the effect
     *
     * @return {R, SW, S, RAND, GF, GE, GM, NULL}
     */
    public String getEffect() {
        return this.effect;
    }
    
    
    /** Gets the color
     *
     * @return {RED, BLUE, GREEN}
     */
    public String getColor() {
        return this.color;
    }
    
    
    /** Gets the path
     *
     * @return "resources/{$name}.png"
     */
    public String getPath() {
        return "resources/" + this.path + ".png";
    }
    
    
    /** Gets Debug data
     *
     * @return A string containing all info assigned to the shape
     */
    public String getDebugData() {
        return "Color: " + this.color + ", Shape: " + this.shape + ", Effect: " + this.effect;
    }
    
    
    /** Changes the shapes color randomly
     *
     */
    public void newColor() {
        switch((int) ((Math.random() * 3) + 1)) {
            case(1): {
                this.color = "RED";
                break;
            }
            case(2): {
                this.color = "BLUE";
                break;
            }
            case(3): {
                this.color = "GREEN";
                break;
            }
        }
        
        this.path = this.shape + "-" + this.color;
    }
}
