
import javafx.scene.image.Image;

/**
 * Class containing instance variables and methods for crafting parrots.
 * The user can create parrots and manipulate its status
 * @author Rodrigo Wong Mac #000887648
 */
public class Parrot {

    // Attributes

    /** Parrot's name **/
    private String parrotName = "Julius";
    /** Parrot's health **/
    private int health = 3;
    /** Amount of crumbs (kg) in Parrot's stomach **/
    private double crumbs = 0.1;
    /** Is the parrot tamed? **/
    private boolean isTamed = false;
    /** Is the parrot alive? **/
    private boolean isAlive = true;
    /** Is the parrot flying? **/
    private boolean isFlying = true;


    // Setters

    /**
     * Give a name to the parrot
     * @param parrotName Name given to the parrot
     **/
    public void setParrotName(String parrotName) {
        this.parrotName = parrotName;
    }

    /**
     * Set amount of crumbs in the parrots stomach
     * @param crumbs The amount of crumbs in the parrot's stomach
     **/
    public void setCrumbs(double crumbs) {this.crumbs = crumbs;}

    // Getters

    /**
     * access to parrot's name
     * @return The parrot's name
     **/
    public String getParrotName() {
        return parrotName;
    }

    /**
     * access to parrot's flying status
     * @return True if parrot is flying, False if is sitting
     */
    public Boolean getIsFlying(){ return isFlying; }

    /**
     * access to see if parrot is alive
     * @return True if parrot is alive, False if parrot is dead
     */
    public Boolean getIsAlive(){ return isAlive; }

    /**
     * Parrot constructor to make new parrots
     * @param parrotName Name given to the parrot
     * @param crumbs Amount of crumbs in parrot's stomach
     **/
    public Parrot(String parrotName, double crumbs) {
        setParrotName(parrotName);
        setCrumbs(crumbs);
    }
    // Methods

    /**
     * Feed crumbs to the parrot
     * @param amount The amount of crumbs being fed to the parrot
     * @return message with new updated information about the parrot's status
     **/

    public String feed(double amount) {

        // Strings to generate final message
        String tamed = ""; // chance of taming parrot
        String sick = ""; // parrots is sick if it has more than 2.5kg of crumbs
        String yummy = ""; // parrots under 2.5kg of crumb says yummy
        String message; // final message
        // Changes made if amount of crumbs fed is valid
        if (amount > 0) {
            // Changes made if an alive parrot is fed
            if (isAlive) {
                double random = Math.random() * 100;
                crumbs += amount;
                // Probability of taming a parrot
                if (((20 * amount) >= random) && !isTamed) {
                    isTamed = true;
                    tamed = String.format("You TAMED %s!\n", parrotName);
                }
                // Changes made if parrot is fed to its max amount of crumbs
                if (crumbs >= 2.5) {
                    health -= 2;
                    yummy = String.format("%s is feeling full, but can not stop eating!\n", parrotName);
                    sick = String.format("Now %s is feeling sick from overeating...\n", parrotName);
                    // Parrots dies if health goes to 0
                    if (health <= 0) {
                        yummy = "";
                        sick = String.format("You just forced fed %s to death...\n", parrotName);
                        isAlive = false;
                        health = 0;
                    }
                }
                // Changes made if parrot is not full yet
                else if (crumbs < 2.5) {
                    health += 1;
                    yummy = "YUMMY!\n";
                    if (health >= 3) {
                        health = 3;
                    }
                }
                message = tamed + yummy + sick;
            }
            // Changes made if parrot is dead
            else {
                message = "You can not feed a DEAD parrot\n";
            }
        }
        // Changes made if amount of crumbs is not valid
        else {
            message = "Are you trying to feed me or take food away from me?!\n";
        }
        return message;
    }

    /**
     * Command the parrot to either fly or stay
     * @param commandChoice The command given to the parrot (either fly or stay)
     * @return message with new updated information about the parrot's status
     **/
    public String command(String commandChoice) {
        // String for final message
        String message; // final message
        // Changes made if parrot is dead
        if (!isAlive) {
            message = "You can not command a DEAD parrot...\n";
        }
        // Changes made if parrot is alive
        else {
            // Changes made if parrot is tamed
            if (isTamed) {
                // Command to fly
                if (commandChoice.equalsIgnoreCase("fly")) {
                    isFlying = true;
                    message = String.format("%s is flying\n", parrotName);
                }
                // Command to stay
                else if (commandChoice.equalsIgnoreCase("stay")) {
                    isFlying = false;
                    message = String.format("%s is sitting\n", parrotName);
                }
                // Unknown commands
                else {
                    message = String.format("%s is confused... Please just tell him to either FLY or STAY!\n", parrotName);
                }
            }
            // Changes made if parrot is not tamed
            else {
                message = "You can not command an UNTAMED parrot...\n";
            }
        }
        return message;
    }

    /**
     * Hit the parrot
     * @return message with new updated information about the parrot's status
     **/
    public String hit() {

        // String to store final message
        String message; // final message
        // Changes made if parrot is alive
        if (isAlive) {
            // Changes made if parrot is tamed
            if (isTamed) {
                message = String.format("OUCH! %s is no longer loyal to you...\n", parrotName);
            }
            // Changes made if parrot is untamed
            else {
                message = "Ouch!";
            }
            health -= 1;
            isTamed = false;
            isFlying = true;

            // Changes if parrot dies
            if (health <= 0) {
                isAlive = false;
                health = 0;
                message = String.format("You just murdered %s\n", parrotName);
            }
        }
        // Changes if parrot is dead
        else {
            message = "Only a psycho would hit a DEAD parrot!\n";
        }
        return message;
    }

    /**
     * Compare two Parrots and make them both play together
     * @param other comparison to another instance of parrot
     * @return message with new updated information about the parrot's status
     **/
    public String play(Parrot other) {
        // String to store final message
        String message; // final message
        // Can only play with parrots that are both tamed and alive
        if ((isTamed && other.isTamed) && (isAlive && other.isAlive)) {
            if (parrotName.equalsIgnoreCase(other.getParrotName())){
                message = String.format("%s can not play alone", parrotName);
            }
            else {
                isTamed = false;
                isFlying = true;
                other.isTamed = false;
                other.isFlying = true;
                message = String.format("%s is having fun with %s. So much that they both fly away!\n", parrotName, other.parrotName);
            }
        }
        // Changes made if requirements to play is not met
        else {
            message = String.format("%s can not play with %s\n", parrotName, other.parrotName);
        }

        return message;
    }

    /**
     * Method to draw an image of the parrot either flying or sitting (if alive)
     * or draw an image of the dead parrot (if dead)
     * @param img_flying image of a parrot flying
     * @param img_stay image of a parrot sitting
     * @param img_dead image of a dead parrot
     * @return one of the parameters
     */
    public Image draw(Image img_flying, Image img_stay, Image img_dead){
        Image image;
        if (isAlive) {
            if (isFlying){
              image  = img_flying;
            }
            else {
                image = img_stay;
            }
        }
        else{
            image = img_dead;
        }
        return image;
    }

    // Convert into printable strings
    /**
     * Create two strings to show parrot's status and information
     * @return Formatted message with parrots information
     */
    @Override
    public String toString() {
        // String to store final message
        String message; // final message

        String t; // translate boolean value of isTamed into string
        String f; // translate boolean value of isFlying into string

        // Turn boolean values for isTamed and isFlying into strings
        if (isTamed) t = "Tamed";
        else t = "Untamed";
        if (isFlying) f = "flying";
        else f = "sitting";

        // String generated if parrot is alive
        if (isAlive) {
            message = String.format("%s Parrot %s: %.2f kg of crumbs, %d hearts, %s", t, parrotName, crumbs, health, f);
        }
        // String generated if parrot is dead
        else {
            message = String.format("%s DEAD Parrot %s: %.2f kg of crumbs, %d hearts", t, parrotName, crumbs, health);
        }
        return message;
    }
}
