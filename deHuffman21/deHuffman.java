// Name:              Date:
   import java.util.Scanner;
   import java.io.*;
   import java.util.*;
    public class deHuffman
   {
      /*
       *    The main method should ask the user to enter a name for the file which will 
       * be the center part of the message and Huffman code filenames.  If the user enters 
       * "special", then the program should look for the code file with name scheme_special.txt
       * and the message file at message_special.txt  The codes are loaded into the tree and
       * then used to translate the message.
       */

       public static void main(String[] args) throws IOException
      {
      //Ask the user to enter the middle part of the filenames
         Scanner in = new Scanner(System.in);
         System.out.println("Please enter the middle part of the filename");
         String filename = in.nextLine();
      //Open a scanner on the message.filename.txt and another on scheme.filename.txt
         File mess = new File("message." + filename + ".txt");
         File scheme = new File("scheme." + filename + ".txt"); 
         Scanner readm = new Scanner(mess);
         Scanner reads = new Scanner(scheme);
      //Read in the message from the message text file, then you are done with that scanner
         String message = "";
         while(readm.hasNextLine())
         {
            message += readm.nextLine();
         }    
      //Now call the huffmanTree method, passing it the scheme Scanner so it can read
      //  and construct the tree.
         TreeNode pass = huffmanTree(reads);
      //Then pass the encoded message and the root of the tree to dehuff to uncompress
      //  it.  Print the result of this method.
         System.out.println(dehuff(message,pass));
      }
      
      /*
       *    Method huffmanTree is passed a scanner that is already linked to the file
       * containing the Huffman codes to load.  These should be loaded into a binary
       * tree where the left child represents a 0 and the right child represents a 1.
       * Nodes which are not leaves have a null value, while leaves hold the letter
       * corresponding to the path of 0s and 1s followed to get to the leaf.
       * No recursion is needed in this method.
       */

       public static TreeNode huffmanTree(Scanner schemeScan)
      {
      //Create the root node and save a reference to it (you have to return it at the end)
         TreeNode tree = new TreeNode(null);
      //Let the file reading drive the processing.  Read each line until there are no more lines.
         ArrayList<String> letter = new ArrayList<String>();
         ArrayList<String> list = new ArrayList<String>();
         String scheme = "";
         while(schemeScan.hasNextLine())
         {
            scheme = schemeScan.nextLine();
            list.add(scheme);
            letter.add(scheme);
         }
      //For each line, take off the first letter and save it - it is the actual letter.
         for(int i = 0; i < list.size(); i++)
         {
            String single = list.get(i).substring(0,1);
            letter.set(i, single);
            String modify = list.get(i).substring(1, list.get(i).length());
            list.set(i, modify);
         }
      //Now read the ones and the zeroes.  Keep track of the current node you are on.
      //  When you find a zero, move to the left.  when you find a one, move right.
      //  If there is a node there keep going, and if the node is null,
      //  create a new node with a null VALUE and attach it to the current node.
      //  When you run out of ones and zeroes in the code, set the current node's value to
      //    the letter. (This should be the leaf at the end of the code path.)  
         TreeNode root = tree;
         for(int a = 0; a < list.size(); a++)
         {
            for(int b = 0; b < list.get(a).length(); b++)
            {
               if(b == list.get(a).length() - 1)
               {
                  if(String.valueOf(list.get(a).charAt(b)).equals("0"))
                  {
                     TreeNode value = new TreeNode(letter.get(a));
                     root.setLeft(value);
                     //root.setValue(letter.get(a));
                     root = tree;
                  }
                  else if(String.valueOf(list.get(a).charAt(b)).equals("1"))
                  {
                     TreeNode val = new TreeNode(letter.get(a));
                     root.setRight(val);
                     root = tree;
                  }
               }
               else if(String.valueOf(list.get(a).charAt(b)).equals("0"))
               {
                  //root.setLeft(null);
                  if(root.getLeft() != null)
                  {
                     root = root.getLeft();
                  }
                  else
                  {
                     TreeNode left = new TreeNode(null);
                     root.setLeft(left);
                     root = root.getLeft();
                  }
                  //root.setValue(null);
               }
               //else 
               else if(String.valueOf(list.get(a).charAt(b)).equals("1"))
               {
                  if(root.getRight() != null)
                  {
                     root = root.getRight();
                  }
                  else
                  {
                     TreeNode right = new TreeNode(null);
                     root.setRight(right);
                     root = root.getRight();
                  }
                  //root.setValue(null);
               }
               //else 
            }
         }
         return tree;
      }
      
      /*
       *    Method dehuff is passed the encoded text to be decoded, and the root of the tree that was
       * built by the huffmanTree method.  Characters are read from the text String and direct
       * the movement through the tree.  When a leaf is reached, a letter has been decoded and
       * can be added to the decoded message.  Then the next part of the message will begin
       * again at the root and trace down to a letter of the tree again.  When the message is 
       * completely decoded, the decoded version of the message is returned.
       * You can do this method recursively or iteratively.  (Iteratively is easier here)
       * If you want to do recursion, you should add the top node as another parameter
       */
       public static String dehuff(String text, TreeNode root)
      {
         //read through the tree, following the text's ones and zeroes, until you
         //   reach a letter (VALUE not equal to null).
         String message = "";
         TreeNode access = root;
         for(int i = 0; i < text.length(); i++)
         {
            
            if(String.valueOf(text.charAt(i)).equals("0"))
            {
               access = access.getLeft();
               if(access.getValue() != null)
               {
                  message += access.getValue();
                  access = root;
               }
            }
            else if(String.valueOf(text.charAt(i)).equals("1"))
            {
               access = access.getRight();
               if(access.getValue() != null)
               {
                  message += access.getValue();
                  access = root;
               }
            }
         }
         // When you reach a letter, add it to the message and then start again at 
         //   the top of the Huffman tree.
         
         // Keep track of the current node you are on and loop through the digits of the message.
         return message;
      }
   }