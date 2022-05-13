/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
    to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit 
    this template
 */

/**
 *
 * @author sophietotterstrom
 */

import java.util.*;

public class Parameters {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        // ensin järjestetään 
        Arrays.sort(args);
        
        // etsitään suurin rivinumero
        int maxRiviNumero = args.length;
        String maxRiviNumeroString = maxRiviNumero+"";
        
        // Ensimmäisen sarakkeen levein arvo on viimeisen rivin rivinumero
        // otetaan sen String muodon pituus, tämä on nyt sarakkeen leveys
        int ekaSarakeLeveys = maxRiviNumeroString.length();
       
        // Toisen sarakkeen levein arvo on pisin komentoriviparametri
        int tokaSarakeLeveys = 0;
        for (int j = 0; j < args.length; j++)
        {
            String nimi = args[j];
            if (j == 0)
            {
                tokaSarakeLeveys = nimi.length();
            }
            else
            {
                if (tokaSarakeLeveys < nimi.length())
                {
                    tokaSarakeLeveys = nimi.length();
                }
            }
        }
        
        // kootaan yhteen tulostuksia
        int kokonaisLeveys = (ekaSarakeLeveys+tokaSarakeLeveys)+7;
        String riviMerkkiEka = "#";
        String riviMerkkiToka = "-";
        String riviMerkkiKolmas = "+";
        String kokoRiviEka = riviMerkkiEka.repeat((kokonaisLeveys));
        String kokoRiviToka = riviMerkkiEka 
                                + riviMerkkiToka.repeat(ekaSarakeLeveys+2) 
                                + riviMerkkiKolmas
                                + riviMerkkiToka.repeat(tokaSarakeLeveys+2)
                                + riviMerkkiEka;

        
        // jotta saadaan aina oikea rivinumero
        int riviCounter = 1;
        
        // tämä tulostaa koko taulukon
        int kaikkiRivit = (2*maxRiviNumero)+1;
        
        for (int i = 1; i<=kaikkiRivit; i++)
        {
            // taulukon eka tai vika rivi
            if ((i == 1) || (i == kaikkiRivit))
            {
                System.out.println(kokoRiviEka);
            }
            
            // pariton rivi, eli ns. välirivi
            else if (i % 2 != 0)
            {
                System.out.println(kokoRiviToka);
            }
            
            // ns. normirivit
            else
            {
                // alkumerkki ja väli
                System.out.print(riviMerkkiEka + " ");
                
                // sitten rivinumero oikealle tasattuna
                String format = "%"+ekaSarakeLeveys+"d";
                System.out.printf(format, riviCounter);
                
                // ja sitten sarakkeiden väli
                System.out.print(" | ");
                
                // sitten teksti vasemmalle tasattuna
                String formatKaks = "%"+"-"+tokaSarakeLeveys+"s";
                System.out.printf(formatKaks, args[riviCounter-1]);
                
                // ja rivin lopetus
                System.out.println(" "+riviMerkkiEka);
                
                // lopuksi vielä
                riviCounter++;
            }
        }
        
    }
}
