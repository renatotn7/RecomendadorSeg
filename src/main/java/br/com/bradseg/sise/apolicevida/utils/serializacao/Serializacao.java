package br.com.bradseg.sise.apolicevida.utils.serializacao;

import java.io.File;
  import java.io.IOException;
  import java.util.ArrayList;
import java.util.List;

import net.librec.recommender.Recommender;

import java.io.FileInputStream;
  import java.io.FileOutputStream;
  import java.io.ObjectInputStream;
  import java.io.ObjectOutputStream;

  public class Serializacao {

    // serializa��o: gravando o objetos no arquivo bin�rio "nomeArq"
    public static void gravarArquivoBinario(final Object recommender, final String nomeArq) {
      File arq = new File(nomeArq);
      try {
        arq.delete();
        arq.createNewFile();

        ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(arq));
        objOutput.writeObject(recommender);
        objOutput.close();

      } catch(IOException erro) {
    	  erro.printStackTrace();
          System.out.printf("Erro: %s", erro.getMessage());
      }
    }

    // desserializa��o: recuperando os objetos gravados no arquivo bin�rio "nomeArq"
    public static List<Object> lerArquivoBinario(String nomeArq) {
      List<Object> lista = new ArrayList();
      try {
        File arq = new File(nomeArq);
        if (arq.exists()) {
           ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(arq));
           lista = (ArrayList<Object>)objInput.readObject();
           objInput.close();
        }
      } catch(IOException erro1) {
          System.out.printf("Erro: %s", erro1.getMessage());
      } catch(ClassNotFoundException erro2) {
          System.out.printf("Erro: %s", erro2.getMessage());
      }

      return(lista);
    }

  }