package climate;

import java.util.ArrayList;

import javax.xml.crypto.Data;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        String [] inputArr = inputLine.split(",");
        StateNode obj = new StateNode(inputArr[2], null, null);
        StateNode ptr = firstState;
        if (ptr == null)
        {
            firstState = obj;

        }
        while (ptr != null)
        {
            if (ptr.getName().equals(inputArr[2]))
            {
                return;
            }
            if (ptr.getNext() == null)
            {
                ptr.setNext(obj);
            }
            ptr = ptr.getNext();
        }
    

    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel (String inputLine) {


        // WRITE YOUR CODE HERE
        String [] inputArr = inputLine.split(",");
        StateNode statePTR = firstState;
        CountyNode obj2 = new CountyNode(inputArr[1], null, null);
        while (statePTR != null) {
            if (statePTR.getName().equals(inputArr[2]))
            {
                CountyNode countyPTR = statePTR.getDown();
                while (countyPTR != null)
                {
                    if(countyPTR.getName().equals(inputArr[1]))
                    {
                        return;
                    }
                    if(countyPTR.getNext() == null)
                    {
                        countyPTR.setNext(obj2);
                    }
                    countyPTR = countyPTR.getNext();

                }
                if (statePTR.getDown() == null) {
                    statePTR.setDown(obj2);
                    return;
                }
            }
                
            
            statePTR = statePTR.getNext();
        }
   
    }
    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
public void addToCommunityLevel(String inputLine) {

    String[] inputArr = inputLine.split(",");
    String stateName = inputArr[2];
    String countyName = inputArr[1];
    String communityName = inputArr[0];
    StateNode statePTR = firstState;

    while (statePTR != null) {
        if (statePTR.getName().equals(stateName)) {
            CountyNode countyPTR = statePTR.getDown();

            while (countyPTR != null) {
                if (countyPTR.getName().equals(countyName)) {
                    CommunityNode commPTR = countyPTR.getDown();
                    CommunityNode placeHolder = null;

       
                    if (commPTR == null) {
                        climate.Data input = new climate.Data(Double.parseDouble(inputArr[3]),Double.parseDouble(inputArr[4]), Double.parseDouble(inputArr[5]), Double.parseDouble(inputArr[8]),
                        Double.parseDouble(inputArr[9]), inputArr[19], Double.parseDouble(inputArr[49]), Double.parseDouble(inputArr[37]), Double.parseDouble(inputArr[121]));
                        CommunityNode newComm = new CommunityNode(communityName, null, input);
                        countyPTR.setDown(newComm);
                        return; 
                    }

                    while (commPTR != null) {
                        if (commPTR.getName().equals(communityName)) {
                            return;
                        }
                        placeHolder = commPTR;  
                        commPTR = commPTR.getNext();
                    }

                    // had to include climate. because compiler was having trouble reading the data initalizations for some reason
                    climate.Data inputData = new climate.Data(Double.parseDouble(inputArr[3]), Double.parseDouble(inputArr[4]), Double.parseDouble(inputArr[5]), Double.parseDouble(inputArr[8]),
                            Double.parseDouble(inputArr[9]), inputArr[19], Double.parseDouble(inputArr[49]), Double.parseDouble(inputArr[37]), Double.parseDouble(inputArr[121]));
                    CommunityNode newComm = new CommunityNode(communityName, null, inputData);
                    placeHolder.setNext(newComm);
                    return; 
                }
                countyPTR = countyPTR.getNext();
            }
        }
        statePTR = statePTR.getNext();
    }
}
 

    

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities(double userPercentage, String race) {
        int count = 0;
        StateNode statePTR = firstState;
    
        while (statePTR != null) {
            CountyNode countyPTR = statePTR.getDown();
    
            while (countyPTR != null) {
                CommunityNode commPTR = countyPTR.getDown();
    
                while (commPTR != null) {
                    if ("True".equalsIgnoreCase(commPTR.getInfo().getAdvantageStatus())) {
                        double racePercentage = 0.0;
    
                        if (race.equalsIgnoreCase("African American")) {
                            racePercentage = commPTR.getInfo().getPrcntAfricanAmerican();
                        } else if (race.equalsIgnoreCase("White American")) {
                            racePercentage = commPTR.getInfo().getPrcntWhite();
                        } else if (race.equalsIgnoreCase("Native American")) {
                            racePercentage = commPTR.getInfo().getPrcntNative();
                        } else if (race.equalsIgnoreCase("Asian American")) {
                            racePercentage = commPTR.getInfo().getPrcntAsian();
                        } else if (race.equalsIgnoreCase("Hispanic American")) {
                            racePercentage = commPTR.getInfo().getPrcntHispanic();
                        }
    
                        if (racePercentage * 100 >= userPercentage) {
                            count++;
                        }
                    }
                    commPTR = commPTR.getNext();
                }
                countyPTR = countyPTR.getNext();
            }
            statePTR = statePTR.getNext();
        }
    
        return count;
    }
    
    
    

    

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        //WRITE YOUR CODE HERE
        int count = 0;
        StateNode statePTR = firstState;
    
        while (statePTR != null) {
            CountyNode countyPTR = statePTR.getDown();
    
            while (countyPTR != null) {
                CommunityNode commPTR = countyPTR.getDown();
    
                while (commPTR != null) {
                    if ("False".equalsIgnoreCase(commPTR.getInfo().getAdvantageStatus())) {
                        double racePercentage = 0.0;
    
                        if (race.equalsIgnoreCase("African American")) {
                            racePercentage = commPTR.getInfo().getPrcntAfricanAmerican();
                        } else if (race.equalsIgnoreCase("White American")) {
                            racePercentage = commPTR.getInfo().getPrcntWhite();
                        } else if (race.equalsIgnoreCase("Native American")) {
                            racePercentage = commPTR.getInfo().getPrcntNative();
                        } else if (race.equalsIgnoreCase("Asian American")) {
                            racePercentage = commPTR.getInfo().getPrcntAsian();
                        } else if (race.equalsIgnoreCase("Hispanic American")) {
                            racePercentage = commPTR.getInfo().getPrcntHispanic();
                        }
    
                        if (racePercentage * 100 >= userPrcntage) {
                            count++;
                        }
                    }
                    commPTR = commPTR.getNext();
                }
                countyPTR = countyPTR.getNext();
            }
            statePTR = statePTR.getNext();
        }
    
        return count;
    
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 

    public ArrayList<StateNode> statesPMLevels(double PMlevel) {
        ArrayList<StateNode> statesList = new ArrayList<>();
        StateNode currentState = firstState;
    
        while (currentState != null) {
            CountyNode currentCounty = currentState.getDown();
    
            while (currentCounty != null) {
                CommunityNode currentComm = currentCounty.getDown();
    
                while (currentComm != null) {
                    if (currentComm.getInfo().getPMlevel() >= PMlevel) {
                    
                        if (!(statesList.contains(currentState))) {
                            statesList.add(currentState);
                        }
                        break;  
                    }
                    currentComm = currentComm.getNext();
                }
                currentCounty = currentCounty.getNext();
            }
            currentState = currentState.getNext();
        }
    
        return statesList;
    }
    

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood(double userPercentage) {
        int count = 0;
        StateNode currentState = firstState;
    
        while (currentState != null) {
            CountyNode currentCounty = currentState.getDown();
    
            while (currentCounty != null) {
                CommunityNode currentComm = currentCounty.getDown();
    
                while (currentComm != null) {
                    if (currentComm.getInfo().getChanceOfFlood() >= userPercentage) {
                        count++;
                    }
                    currentComm = currentComm.getNext();
                }
                currentCounty = currentCounty.getNext();
            }
            currentState = currentState.getNext();
        }
    
        return count;
    }
    
    

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities(String stateName) {
        ArrayList<CommunityNode> commlist = new ArrayList<>();
        CommunityNode min = null;
        StateNode ptr = firstState;
    
        while (ptr != null) {
            if (ptr.getName().equals(stateName)) {
                CountyNode ptr2 = ptr.getDown();
    
                while (ptr2 != null) {
                    CommunityNode ptr3 = ptr2.getDown();
    
                    while (ptr3 != null) {
                        if (commlist.size() < 10) {
                            commlist.add(ptr3);
                        } else {
                            min = commlist.get(0);
                            int minIndex = 0;
    
                            for (int i = 0; i < commlist.size(); i++) {
                                if (commlist.get(i).getInfo().getPercentPovertyLine() < min.getInfo().getPercentPovertyLine()) {
                                    min = commlist.get(i);
                                    minIndex = i;
                                }
                            }
    
                            if (ptr3.getInfo().getPercentPovertyLine() > min.getInfo().getPercentPovertyLine()) {
                                commlist.set(minIndex, ptr3);
                            }
                        }
    
                        ptr3 = ptr3.getNext();  // Move to the next community
                    }
    
                    ptr2 = ptr2.getNext();  // Move to the next county
                }
            }
    
            ptr = ptr.getNext();  // Move to the next state
        }
    
        return commlist;
    }
    
}
    
