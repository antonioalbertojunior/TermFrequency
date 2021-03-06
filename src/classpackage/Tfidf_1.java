/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classpackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.log10;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alberto
 */
public class Tfidf_1 {

    private static final List<Map> listMap = new ArrayList<>();
    double matrizSimetrica[][];
    double matrizSimetricaTFIDF[][];
    double matrizFrequencia[][];
    double matrizFrequenciaIDF[][];
    List<String> vetorMundi = new ArrayList<String>();
    List<Integer> vetorQuant = new ArrayList<>();
    List<Double> vetorLog = new ArrayList<>();
    String array[];

    //FileWriter arquivo;
    //vetor de stowords
    String stopwords[] = {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        ".", ",", "!", ";", ":", "?", ")", "(",
        " para ", " até ", " do ", " da ", " de ", " se ", " te ",
        " um ", " como ", " Então ", " pois ", " sua ", " que ",
        " na ", " no ", " eu ", " em ", " o ", " a ", " E ", " com ",
        " tua ", " dos ", " meu ", " ao ", " me ", " seu ", " as ", " os ",
        " e ", " lhe ", " teu ", " por ", " tem ", " ter ", " A ", " O ", " As ", " Da ",
        " De ", " Do ", " uma ", " dum ", " qual ", " eram ", " carga ",
        " tal ", " tenho ", " tudo ", " onde ", " ia ", " ir ", " de ", " da", " do",
        " vem ", " vez", " outra ", " esse ", " este ", " esta ", " nesse ",};
    String vetTxt[] = {
        "dados/txt1.txt", "dados/txt2.txt", "dados/txt3.txt",
        "dados/txt4.txt", "dados/txt5.txt"
    };

    public void salvarArquivoLista() {
        try {
            FileReader arq;

            System.out.printf("\nConteúdo do arquivo texto:\n");
            List<FileReader> listArq = new ArrayList<>();//lista para cada arquivo

            for (int i = 0; i < vetTxt.length; i++) {
                arq = new FileReader(vetTxt[i]);
                listArq.add(arq); // vetor recebe todos os arquivos
            }
            for (int r = 0; r < vetTxt.length; r++) {

                Map<String, Double> map = new HashMap<>();
                System.out.print("Arquivo " + r + " ");
                arq = listArq.get(r);
                int k = 0;
                BufferedReader lerArq = new BufferedReader(arq);
                String linha = lerArq.readLine();//le primeira linha do arquivo
                List<String> vetorLinha = new ArrayList<>();
                ArrayList<String> vetWord = new ArrayList<>();
                while (linha != null) {
                    for (int a = 0; a < stopwords.length; a++) { //para cada palavra do tipo stopword
                        linha = linha.replace(stopwords[a], " "); //trocar por espaco
                    }
                    vetorLinha.add(linha);//adiciona num arraylist
                    String vetor[] = vetorLinha.get(k).split(" ");//retorna um vetor separador por espaco.. para cada linha
                    for (int l = 0; l < vetor.length; l++) {//enquanto nao acabar o vetor para aquela linha
                        vetWord.add(vetor[l]);//adiciona em um arraylist com todas as palavras
                    }
                    linha = lerArq.readLine();
                    k++;
                }
                arq.close();

                //para cada elemento adicionado no vetor
                //pegar o elemento e andar uma vez no vetWord e retornar a quantidade
                int cont = 0;
                Iterator<String> itrV = vetWord.iterator();//Iterator com todos elementos do texto exceto stopwords

                while (itrV.hasNext()) {
                    Object element2 = itrV.next();
                    if (map.containsKey(element2.toString())) {
                        map.put(element2.toString(), map.get(element2.toString()) + 1);
                    } else {
                        map.put(element2.toString(), 1.0);
                    }
                    cont++;
                }
                for (String key : map.keySet()) {
                    double frequencia = (map.get(key) / cont);
                    map.replace(key, frequencia);

                }
                System.out.print(cont + "\n");
                listMap.add(map);//adicionar na lista de mapas
                //map = new HashMap<>();//instancia para proxima iteração
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void umaVez() {
        //1 -TODAS AS PALAVRAS QUE APARACEM PELO MENOS UMA VEZ NA BIBLIA DESCONSIDERANDO STOPWORDS;
        // List<String> vetorMundi = new ArrayList<String>();
        Map<String, Double> mapAux = new HashMap<String, Double>();
        for (int k = 0; k < vetTxt.length; k++) {
            mapAux.putAll(listMap.get(k));

            for (String key : mapAux.keySet()) {
                if (!vetorMundi.contains(key)) {
                    vetorMundi.add(key);
                }
            }
            mapAux = new HashMap();
        }
        array = new String[vetorMundi.size()];
        for (int u = 0; u < vetorMundi.size(); u++) {
            array[u] = vetorMundi.get(u);
            //System.out.println(array[u]);
        }

    }

    public void tf() {

        Map<String, Double> mapAux = new HashMap<String, Double>();
        for (int i = 0; i < vetorMundi.size(); i++) {
            for (int k = 0; k < vetTxt.length; k++) {
                System.out.println("Arquivo-------->" + k);
                mapAux.putAll(listMap.get(k));

                for (String key : mapAux.keySet()) {
                    //palavras que contem pelo menos uma vez nos txts
                    if (!vetorMundi.contains(key)) {
                        System.out.println("Arquivo " + k + " Chave   " + key + "     Valor " + mapAux.get(key));
                        vetorMundi.add(key);
                    }
                }
                mapAux = new HashMap();
            }
        }
    }

    public void idf() {
        double valor = Double.valueOf(vetTxt.length);
        Map<String, Double> mapAux = new HashMap<String, Double>();
        // System.out.println(vetorMundi.size());
        for (int i = 0; i < vetorMundi.size(); i++) {//para cada palavra que contem nos textos
            int cont = 0;
            for (int k = 0; k < vetTxt.length; k++) {//para cada arquivo
                mapAux.putAll(listMap.get(k));
                if (mapAux.containsKey(vetorMundi.get(i))) {//se o mapa atual contem a palavra
                    cont++;
                }
                mapAux = new HashMap();
            }
            vetorQuant.add(cont);
        }
        

        // System.out.print(vetorMundi.size());
        for (int v = 0; v < vetorQuant.size(); v++) {
            double value=  Double.valueOf( vetorQuant.get(v));
            double div= ((valor)/(value));
            double LogIdf = log10(div);
            //System.out.println(LogIdf);
            vetorLog.add(LogIdf);
            //System.out.println("Quantidade "+ vetorQuant.get(v)+" valor  "+vetorLog.get(v));
        }
       // vetorLog.size();
        // idf log(vetTxt.length quantDocs/se o termo aparece no documento cont++)
    }

    public void tfidf() {
        matrizFrequenciaIDF=new double[vetTxt.length][vetorLog.size()];
        for(int i=0;i< vetTxt.length;i++){
            for(int j=0;j<vetorLog.size();j++){
                     matrizFrequenciaIDF[i][j]=vetorLog.get(j)*matrizFrequencia[i][j]; 
             //         System.out.print(matrizFrequenciaIDF[i][j]+" ");
                }
           // System.out.print("\n");
        }
  
        //tf*idf para cada palavra
        //              palavra 1 palavra 2... palavra n
        //Arquivo 1     tfidfP1   tfidfP2
        //Arquivo 2
        //... retornar matriz
        //distancia euclidiana
    }
    
    public void matrizSimetricaTFIDF() {
        // MATRIZ DistanciaEuclidiana
        System.out.print("\nMatriz Distancia Euclidiana");
        for (int linha = 0; linha < vetTxt.length; linha++) {//linha 0
            System.out.print("\n");
            for (int coluna = 0; coluna < vetorLog.size(); coluna++) {// coluna 0
                System.out.print(" " + matrizFrequenciaIDF[linha][coluna]);
            }
        }

        double array1[] = new double[array.length];
        double array2[] = new double[array.length];
        matrizSimetricaTFIDF = new double[vetTxt.length][vetTxt.length];

        for (int linha = 0; linha < vetTxt.length; linha++) {
            for (int i = 0; i < array.length; i++) {
                array1[i] = matrizFrequenciaIDF[linha][i];
            }
            for (int coluna = 0; coluna < vetTxt.length; coluna++) {
                double distance = 0;
                for (int j = 0; j < array.length; j++) {
                    array2[j] =matrizFrequenciaIDF[coluna][j];
                }
                distance = EuclideanDistance.calculateDistance(array1, array2);
                matrizSimetricaTFIDF[linha][coluna] = distance;
                
            }
        }
        
    }

    public void showMatrizSimetricaTFIDF(){
          try {
            System.out.println("\nMatriz de Simetria");
            //arquivo = new FileWriter(new File("matriz.txt"));

            for (int g = 0; g < vetTxt.length; g++) {
                System.out.print("[");
                //arquivo.write("[");
                for (int y = 0; y < vetTxt.length; y++) {
                    if (y == vetTxt.length - 1) {
                        // arquivo.write(" " + matrizSimetrica[g][y]);
                        System.out.print(" " + matrizSimetricaTFIDF[g][y]);
                    } else {
                        //arquivo.write(" " + matrizSimetrica[g][y] + ",");
                        System.out.print(" " + matrizSimetricaTFIDF[g][y] + ",");
                    }
                }
                if (g == vetTxt.length - 1) {
                    //System.out.print("\n");
                    //arquivo.write("\n");
                    System.out.print("]" + "\n");
                    //arquivo.write("]" + "\n");
                } else {
                    // System.out.print( "\n");
                    //arquivo.write( "\n");
                    System.out.print("]," + "\n");
                    //arquivo.write("]," + "\n");
                }
            }
            //arquivo.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    public void palavrasComuns() {
        //2 - PARA PALAVRAS EM COMUNS ENTRE OS DOCUMENTOS
        Map<String, Double> mapAux = new HashMap<String, Double>();
        Map<String, Double> mapMundi = new HashMap<String, Double>();
        List<String> vetorMundi = new ArrayList<String>();
        int cont = 0;
        mapAux.putAll(listMap.get(0));

        for (String key : mapAux.keySet()) {
            for (int i = 1; i < vetTxt.length; i++) {
                mapMundi = new HashMap();
                mapMundi.putAll(listMap.get(i));
                boolean existe = mapMundi.containsKey(key);

                if (existe == true) {
                    cont++;
                }
                if (cont == vetTxt.length - 1) {
                    vetorMundi.add(key);
                }
            }
            cont = 0;

        }
        //Adicionando o vetorMundi em outro array
        array = new String[vetorMundi.size()];
        for (int g = 0; g < vetorMundi.size(); g++) {
            array[g] = vetorMundi.get(g);
            // System.out.println(vetorMundi.get(g));
        }

    }

    public void conjuntoDefinido() {
        //CONSIDERANDO UM CONJUNTO DE PALAVRAS
        //String array[] = {"Egito", "Deus"};//array de palavras iguais de todos os txt
        array[0] = "Egito";
        array[1] = "Deus";

    }

    public void matrizFrequenciaPalavras() {
        matrizFrequencia = new double[vetTxt.length][array.length];//tamanho do arquivo e tamanho do array.
        Map<String, Double> maps = new HashMap<>();

        for (int linha = 0; linha < vetTxt.length; linha++) {
            maps.putAll(listMap.get(linha));
            for (int coluna = 0; coluna < array.length; coluna++) {
                if (maps.containsKey(array[coluna])) {//se contem a palavra do array semelhante
                    matrizFrequencia[linha][coluna] = maps.get(array[coluna]);//adiciona na matriz
                }
            }
            maps = new HashMap<>();
        }
        for (int linha = 0; linha < vetTxt.length; linha++) {

            for (int coluna = 0; coluna < array.length; coluna++) {
                // System.out.print(matrizFrequencia[linha][coluna] + " ");
            }
            // System.out.println("\n");
        }
    }

    public void matrizSimetricaPalavras() {
        // MATRIZ DE FREQUENCIA DAS PALAVRAS
        System.out.print("\nMatriz de frequencia das palavras");
        for (int linha = 0; linha < vetTxt.length; linha++) {//linha 0
            System.out.print("\n");
            for (int coluna = 0; coluna < array.length; coluna++) {// coluna 0
                System.out.print(" " + matrizFrequencia[linha][coluna]);
            }
        }

        double array1[] = new double[array.length];
        double array2[] = new double[array.length];
        matrizSimetrica = new double[vetTxt.length][vetTxt.length];

        for (int linha = 0; linha < vetTxt.length; linha++) {
            for (int i = 0; i < array.length; i++) {
                array1[i] = matrizFrequencia[linha][i];
            }
            for (int coluna = 0; coluna < vetTxt.length; coluna++) {
                double distance = 0;
                for (int j = 0; j < array.length; j++) {
                    array2[j] = matrizFrequencia[coluna][j];
                }
                // distance = classpackage.EuclideanDistance.calculateDistance(array1, array2);
                matrizSimetrica[linha][coluna] = distance;

            }
        }
    }

    public void salvarArquivo() {
        try {
            System.out.println("\nMatriz de Simetria");
            //arquivo = new FileWriter(new File("matriz.txt"));

            for (int g = 0; g < vetTxt.length; g++) {
                System.out.print("[");
                //arquivo.write("[");
                for (int y = 0; y < vetTxt.length; y++) {
                    if (y == vetTxt.length - 1) {
                        // arquivo.write(" " + matrizSimetrica[g][y]);
                        System.out.print(" " + matrizSimetrica[g][y]);
                    } else {
                        //arquivo.write(" " + matrizSimetrica[g][y] + ",");
                        System.out.print(" " + matrizSimetrica[g][y] + ",");
                    }
                }
                if (g == vetTxt.length - 1) {
                    //System.out.print("\n");
                    //arquivo.write("\n");
                    System.out.print("]" + "\n");
                    //arquivo.write("]" + "\n");
                } else {
                    // System.out.print( "\n");
                    //arquivo.write( "\n");
                    System.out.print("]," + "\n");
                    //arquivo.write("]," + "\n");
                }
            }
            //arquivo.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tfidf_1 tf = new Tfidf_1();
        tf.salvarArquivoLista();
        tf.umaVez();// tf
        tf.matrizFrequenciaPalavras();//tf
        tf.idf();
        tf.tfidf();    
        tf.matrizSimetricaTFIDF();
        tf.showMatrizSimetricaTFIDF();
//tf.palavrasComuns();

        //tf.matrizSimetricaPalavras();
        //tf.salvarArquivo();
    }
}
