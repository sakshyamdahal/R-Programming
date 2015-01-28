import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CleanData {

	private Scanner mInput;
	private Formatter mOutput;
	
	private String mInputFile;
	private String mOutputFile;
	
	public static void main(String[] args)
	{
		CleanData newData = new CleanData("input.txt", "output.txt");
		newData.cleanData();
	}
	
	
	public CleanData(String inputFile, String outputFile)
	{
		mInputFile = inputFile;
		mOutputFile = outputFile;
	}
	
	public void cleanData()
	{
		openFiles();
		readAndWrite();
		closeFile();
	}
	
	// enable user to open file to read and write
	private void openFiles()
	{
		try
		{
			mInput = new Scanner(new File(mInputFile));
			mOutput = new Formatter(mOutputFile);
		}
		catch (FileNotFoundException fileNotFoundException)
		{
			System.err.println("Error opening file");
			System.exit(1);
		}
		catch (SecurityException securityException)
		{
			System.err.println("You donot have write access to the file " + mOutputFile + ".");
			System.exit(1);
		}
		
	}
	
	// read the file and write clean data to other file
	private void readAndWrite()
	{
		// read the header and write it to outputfile
		String header = mInput.nextLine();
	
		mOutput.format("%s\n", header);
		
		// now read rest of the file changing the value of chromosome to 0 if 'x' or 'X'
		while(mInput.hasNext())
		{
			String line = mInput.nextLine();
			
			String[] data = line.split("\t");
			
			try {
				if (data[3].equals("NA") || hasEmptyString(data))
				{
					// ignore
					
				}
				else if (data[3].equals("x") || data[3].equals("X"))
				{
					data[3] = "0";
					mOutput.format("%s\n", join(data, "\t"));
				}
				else
				{
					mOutput.format("%s\n", line);
				}
			} catch (FormatterClosedException formatterClosedException) {
				System.err.println("Error writing to file");
			}
			catch (NoSuchElementException noSuchElementException)
			{
				System.err.println("Invalid input. Please try again.");
				mInput.nextLine();
			}
		}
		
	}
	
	// close the files
	private void closeFile()
	{
		if (mOutput != null)
			mOutput.close();
		
		if (mInput != null)
			mInput.close();
	}
	
	
	// join a string array by a given delimeter
	private String join(String[] array, String delimeter)
	{
		StringBuffer newString = new StringBuffer();
		int length = array.length;
		
		for (int i = 0; i < length - 1; i++)
		{
			newString.append(array[i]+delimeter);
		}
		
		newString.append(array[length - 1]);
		return newString.toString();
		
	}

	// check if the string array has an empty string as a element
	private boolean hasEmptyString(String[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i].equals(""))
				return true;
		}

		return false;
	}
	
	
	
}
