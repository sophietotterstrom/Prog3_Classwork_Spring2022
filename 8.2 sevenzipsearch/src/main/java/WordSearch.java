import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.*;
import java.util.*;

public class WordSearch
{
    public static void main(String[] args) throws IOException
    {
        String sevenZFileName = args[0];
        String searchWord = args[1];
        SevenZFile sevenZFileObj = new SevenZFile(new File(sevenZFileName));

        SevenZArchiveEntry entry;
        while((entry=sevenZFileObj.getNextEntry())!=null)
        {
            if (entry.getName().contains(".txt"))
            {
                System.out.println(entry.getName());


                ByteArrayOutputStream contentBytes = new ByteArrayOutputStream();

                // ... using a small buffer byte array.
                byte[] buffer = new byte[2048];
                int bytesRead;
                while((bytesRead = sevenZFileObj.read(buffer)) != -1)
                {
                    contentBytes.write(buffer, 0, bytesRead);
                }
                // Assuming the content is a UTF-8 text file we can interpret the
                // bytes as a string.
                String content = contentBytes.toString("UTF-8");
                // System.out.println(content);

                int index = 1;
                for(String line : content.split("\\r?\\n"))
                {
                    if (line.toLowerCase().contains(searchWord.toLowerCase()))
                    {
                        System.out.println(index + ": " + line.replaceAll("(?i)"+searchWord, searchWord.toUpperCase()));
                    }
                    index++;
                }

                System.out.println();
            }

        }
    }

    /*
    open 7z
    look for .txt-files

    for txtFile : files
        print filename

        // perform file search
        int index = 1
        for line : file

            for word : line
                if word.lowerCase() == searchWord
                    upperLine = makeLineUpper(searchWord, line);

                    print(index + " : " + upperLine)

            index++;

        print new extra line
     */
        //SevenZFile sevenZFile = new SevenZFile(new File("archive.7z"));

}