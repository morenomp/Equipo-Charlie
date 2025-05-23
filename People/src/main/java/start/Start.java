package start;

import controller.ControllerImplementation;
import controller.IController;
import view.DataStorageSelection;
import view.login;

/**
 * This class contains the main method, the entry point to the application.
 * @author Francesc Perez
 * @version 1.1.0
 */
public class Start {
    
    /**
     * The method starts the application through the "cont" object of the 
     * ControllerImplementation class. The constructor of this class requires 
     * the dSS object of the DataStorageSelection class as an input argument to 
     * first determine what type of storage system to use.
     * @param args The application does not need input parameters to run
     * @author Francesc Perez
     * @version 1.1.0
     */
    public static void main(String[] args) {
        login loginFrame = new login();
        
        IController cont = new ControllerImplementation(loginFrame);
        cont.start(); 
     }
}
