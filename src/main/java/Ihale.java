import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

 class Ihale {
    private ArrayList iBilgi;

    private Ihale(){
        iBilgi = new ArrayList();
    }

    public static void main(String[] args){
        String dosyaAdi = "İhale Bilgileri.txt";
        SayfaGecis(dosyaAdi);
    }

    private static void SayfaGecis(String dosyaAdi){
        Ihale webOkuyucu = new Ihale();
        for (int i = 1; i <= 20; i++) {
            String url = "https://ilan.gov.tr/kategori-arama?currentPage="+i+"&npdab=on&type=21628";
            webOkuyucu.GetBilgi(url);
        }
        webOkuyucu.DosyaYaz(dosyaAdi);
    }

    private void GetBilgi(String URL){
        try {
            Document dokuman = null;
            dokuman = Jsoup.connect(URL).get();
            Elements bilgiler = dokuman.select("div.col-24, .col-8, .col-16");
            ArrayList<String> geciciListe = new ArrayList<String>();
            for (Element eBilgi : bilgiler){
                geciciListe.add(eBilgi.text());
            }
            for (int i=0; i < geciciListe.size(); i++) {
                if (geciciListe.get(i).equals("İhale Kayıt No")) {
                    iBilgi.add("\n"+ geciciListe.get(i)+ " : "+ geciciListe.get(i+1));
                }
                else if (geciciListe.get(i).equals("Niteliği, Türü ve Miktarı")) {
                    iBilgi.add("\n"+ geciciListe.get(i)+ " : "+ geciciListe.get(i+1));
                }
                else if (geciciListe.get(i).equals("İşin Yapılacağı Yer")) {
                    iBilgi.add("\n"+ geciciListe.get(i)+ " : "+ geciciListe.get(i+1));
                }
                else if (geciciListe.get(i).equals("İhale Türü")) {
                    iBilgi.add("\n"+ geciciListe.get(i)+ " : "+ geciciListe.get(i+1));
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void DosyaYaz(String dosayaAdi){
        try {
            FileWriter yaz = null;
            yaz =new FileWriter(dosayaAdi,true);
            BufferedWriter yazdır = new BufferedWriter(yaz);
            for (int i = 0; i < iBilgi.size();i++){
                try {
                    String temp = iBilgi.get(i)+"\n";

                    yazdır.write(temp);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
            yazdır.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}