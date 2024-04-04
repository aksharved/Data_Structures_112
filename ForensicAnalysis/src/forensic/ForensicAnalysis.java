package forensic;

import org.w3c.dom.Node;

/**
 * This class represents a forensic analysis system that manages DNA data using
 * BSTs.
 * Contains methods to create, read, update, delete, and flag profiles.
 * 
 * @author Kal Pandit
 */
public class ForensicAnalysis {

    private TreeNode treeRoot;            // BST's root
    private String firstUnknownSequence;
    private String secondUnknownSequence;

    public ForensicAnalysis () {
        treeRoot = null;
        firstUnknownSequence = null;
        secondUnknownSequence = null;
    }

    /**
     * Builds a simplified forensic analysis database as a BST and populates unknown sequences.
     * The input file is formatted as follows:
     * 1. one line containing the number of people in the database, say p
     * 2. one line containing first unknown sequence
     * 3. one line containing second unknown sequence
     * 2. for each person (p), this method:
     * - reads the person's name
     * - calls buildSingleProfile to return a single profile.
     * - calls insertPerson on the profile built to insert into BST.
     *      Use the BST insertion algorithm from class to insert.
     * 
     * DO NOT EDIT this method, IMPLEMENT buildSingleProfile and insertPerson.
     * 
     * @param filename the name of the file to read from
     */
    public void buildTree(String filename) {
        // DO NOT EDIT THIS CODE
        StdIn.setFile(filename); // DO NOT remove this line

        // Reads unknown sequences
        String sequence1 = StdIn.readLine();
        firstUnknownSequence = sequence1;
        String sequence2 = StdIn.readLine();
        secondUnknownSequence = sequence2;
        
        int numberOfPeople = Integer.parseInt(StdIn.readLine()); 

        for (int i = 0; i < numberOfPeople; i++) {
            // Reads name, count of STRs
            String fname = StdIn.readString();
            String lname = StdIn.readString();
            String fullName = lname + ", " + fname;
            // Calls buildSingleProfile to create
            Profile profileToAdd = createSingleProfile();
            // Calls insertPerson on that profile: inserts a key-value pair (name, profile)
            insertPerson(fullName, profileToAdd);
        }
    }

    /** 
     * Reads ONE profile from input file and returns a new Profile.
     * Do not add a StdIn.setFile statement, that is done for you in buildTree.
    */
    public Profile createSingleProfile() {

        // WRITE YOUR CODE HERE
        int s = StdIn.readInt();
        STR [] arr = new STR [s];
        for(int i = 0; i<s; i++)
        {
            String name = StdIn.readString();
            int occur = StdIn.readInt();
            STR obj = new STR(name, occur);
            arr[i] = obj;
        }
        Profile prof = new Profile(arr);  
        return prof; // update this line
    }

    /**
     * Inserts a node with a new (key, value) pair into
     * the binary search tree rooted at treeRoot.
     * 
     * Names are the keys, Profiles are the values.
     * USE the compareTo method on keys.
     * 
     * @param newProfile the profile to be inserted
     */
    public void insertPerson(String name, Profile newProfile) {
        TreeNode tree = new TreeNode(name, newProfile, null, null);
    
        if (treeRoot == null) 
        {
            treeRoot = tree;
        } 
        else 
        {
            insertNode(treeRoot, name, newProfile);
        }
    }
    
    private static TreeNode insertNode(TreeNode obj1, String s, Profile p) {
       
        if (obj1 == null)
        {
            TreeNode subTree = new TreeNode(s, p, null, null);
            obj1 = subTree;
            return obj1;
        }
        if((s).compareTo(obj1.getName()) > 0)
        {
            obj1.setRight(insertNode(obj1.getRight(), s, p));
        }
        else if((s).compareTo(obj1.getName()) < 0)
        {
            obj1.setLeft(insertNode(obj1.getLeft(), s, p));
        }
        return obj1;

    }
    

    /**
     * Finds the number of profiles in the BST whose interest status matches
     * isOfInterest.
     *
     * @param isOfInterest the search mode: whether we are searching for unmarked or
     *                     marked profiles. true if yes, false otherwise
     * @return the number of profiles according to the search mode marked
     */
    public int getMatchingProfileCount(boolean isOfInterest) {
        return pCount(treeRoot, isOfInterest);
    }
    
    private int pCount(TreeNode node, boolean isOfInterest) {
        if (node == null) {
            return 0; 
        }
    
        int thisCount = 0;
        if (node.getProfile().getMarkedStatus() == isOfInterest) {
            thisCount++;
        }

        thisCount += pCount(node.getLeft(), isOfInterest);
        thisCount += pCount(node.getRight(), isOfInterest);
    
        return thisCount;
    }
    

    /**
     * Helper method that counts the # of STR occurrences in a sequence.
     * Provided method - DO NOT UPDATE.
     * 
     * @param sequence the sequence to search
     * @param STR      the STR to count occurrences of
     * @return the number of times STR appears in sequence
     */
    private int numberOfOccurrences(String sequence, String STR) {
        
        // DO NOT EDIT THIS CODE
        
        int repeats = 0;
        // STRs can't be greater than a sequence
        if (STR.length() > sequence.length())
            return 0;
        
            // indexOf returns the first index of STR in sequence, -1 if not found
        int lastOccurrence = sequence.indexOf(STR);
        
        while (lastOccurrence != -1) {
            repeats++;
            // Move start index beyond the last found occurrence
            lastOccurrence = sequence.indexOf(STR, lastOccurrence + STR.length());
        }
        return repeats;
    }

    /**
 * Traverses the BST at treeRoot to mark profiles if:
 * - For each STR in profile STRs: at least half of STR occurrences match (round
 * UP)
 * - If occurrences THROUGHOUT DNA (first + second sequence combined) matches
 * occurrences, add a match
 */
public void flagProfilesOfInterest() {
    flagProfiles(treeRoot);
}

private void flagProfiles(TreeNode node) {
    if (node == null) {
        return;
    }

    Profile profile = node.getProfile();
    if (profile == null) {
        return;
    }
    int numSTRs = profile.getStrs().length;

    int matchingSTRs = 0;
    STR [] strs = profile.getStrs();
    for (int i = 0; i<strs.length; i++) {
        int profileOccurrences = strs[i].getOccurrences();
        int occurences1 = numberOfOccurrences(firstUnknownSequence, strs[i].getStrString());
        int occurences2 = numberOfOccurrences(secondUnknownSequence, strs[i].getStrString());
        int sum = occurences1 + occurences2;
        if (profileOccurrences == sum)
            matchingSTRs++;
        int length = strs.length/2;
        if(strs.length%2 != 0) 
            length = length+1;
        

        if (matchingSTRs >= length) {
            profile.setInterestStatus(true); 
        }

    }

    

    flagProfiles(node.getLeft());
    flagProfiles(node.getRight());
}


    /**
     * Uses a level-order traversal to populate an array of unmarked Strings representing unmarked people's names.
     * - USE the getMatchingProfileCount method to get the resulting array length.
     * - USE the provided Queue class to investigate a node and enqueue its
     * neighbors.
     * 
     * @return the array of unmarked people
     */
    public String[] getUnmarkedPeople() {

        // WRITE YOUR CODE HERE
        String [] arr = new String[getMatchingProfileCount(false)];
        Queue<TreeNode> queue = new Queue<>();
        int index = 0;
        if (treeRoot != null)
        {
            queue.enqueue(treeRoot);
        }
        while(queue.isEmpty() != true)
        {
            TreeNode n = queue.dequeue();
            if(n.getProfile().getMarkedStatus() == false)
            {
                arr[index] = n.getName();
                index++;
            }
            
            if (n.getLeft() != null)
            {
                queue.enqueue(n.getLeft());
            }
            if(n.getRight() != null)
            {
                queue.enqueue(n.getRight());
            }
        }
        
        return arr; // update this line
    }

    /**
     * Removes a SINGLE node from the BST rooted at treeRoot, given a full name (Last, First)
     * This is similar to the BST delete we have seen in class.
     * 
     * If a profile containing fullName doesn't exist, do nothing.
     * You may assume that all names are distinct.
     * 
     * @param fullName the full name of the person to delete
     */
    public void removePerson(String fullName) {
        treeRoot = delete(treeRoot, fullName);
    }
    
    private TreeNode findMin(TreeNode node) {
        if (node.getLeft() == null) {
            return node;
        }
        return findMin(node.getLeft());
    }
    
    private TreeNode deleteMin(TreeNode node) {
        if (node.getLeft() == null) {
            return node.getRight();
        }
        node.setLeft(deleteMin(node.getLeft()));
        return node;
    }
    
    private TreeNode delete(TreeNode node, String name) {
        if (node == null) {
            return null;
        }
    
        int compare = name.compareTo(node.getName());
    
        if (compare > 0) {
            node.setRight(delete(node.getRight(), name));
        } else if (compare < 0) {
            node.setLeft(delete(node.getLeft(), name));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                TreeNode successor = findMin(node.getRight());
                node.setName(successor.getName());
                node.setProfile(successor.getProfile());
                node.setRight(deleteMin(node.getRight()));
            }
        }
    
        return node;
    }
    

    /**
     * Clean up the tree by using previously written methods to remove unmarked
     * profiles.
     * Requires the use of getUnmarkedPeople and removePerson.
     */
    public void cleanupTree() {
        // WRITE YOUR CODE HERE

        String[] unmarked = getUnmarkedPeople();
        for(int i = 0; i < unmarked.length; i++){
            removePerson(unmarked[i]);
        }


    }

    /**
     * Gets the root of the binary search tree.
     *
     * @return The root of the binary search tree.
     */
    public TreeNode getTreeRoot() {
        return treeRoot;
    }

    /**
     * Sets the root of the binary search tree.
     *
     * @param newRoot The new root of the binary search tree.
     */
    public void setTreeRoot(TreeNode newRoot) {
        treeRoot = newRoot;
    }

    /**
     * Gets the first unknown sequence.
     * 
     * @return the first unknown sequence.
     */
    public String getFirstUnknownSequence() {
        return firstUnknownSequence;
    }

    /**
     * Sets the first unknown sequence.
     * 
     * @param newFirst the value to set.
     */
    public void setFirstUnknownSequence(String newFirst) {
        firstUnknownSequence = newFirst;
    }

    /**
     * Gets the second unknown sequence.
     * 
     * @return the second unknown sequence.
     */
    public String getSecondUnknownSequence() {
        return secondUnknownSequence;
    }

    /**
     * Sets the second unknown sequence.
     * 
     * @param newSecond the value to set.
     */
    public void setSecondUnknownSequence(String newSecond) {
        secondUnknownSequence = newSecond;
    }

}
