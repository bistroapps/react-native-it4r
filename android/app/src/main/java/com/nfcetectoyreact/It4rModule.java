package com.nfcetectoyreact;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.HashMap;

import br.com.daruma.framework.mobile.DarumaMobile;
import br.com.daruma.framework.mobile.exception.DarumaException;

public class It4rModule extends ReactContextBaseJavaModule {
    private DarumaMobile dmf;
    String strAux;

    ExecutorService service = Executors.newSingleThreadExecutor();

    void mensagem(String msg){
        Toast.makeText(getReactApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    It4rModule(ReactApplicationContext context) {
        super(context);
    }

    //EXTERNALIZAÇÃO DO NOME DO MÓDULO NATIVO QUE SERÁ CHAMADO NO REACT-NATIVE

    @Override
    public String getName() {
        return "It4rModule";
    }

    @ReactMethod
    public void inicializar(ReadableMap initConfig) {

        // INICIALIZAÇÃO DO DISPOSITIVO. REPARE QUE NO ÚLTIMO PARÂMETRO TEMOS O MODELO DO EQUIPAMENTO
        try{
            String params = "@FRAMEWORK(LOGMEMORIA=200;TRATAEXCECAO=FALSE;TIMEOUTWS=10000;SATNATIVO=FALSE)"
            if(initConfig.hasKey("dispositivoName")) {
                // Onde NAME pode ser: V2, V2PRO, K2, K2_MINI, T2_MINI, T2S, D2_MINI, T2S, 
                // "Q4" - para a impressora Q4 da tectoy - via IP e porta
                // "EPSON" - para as impressoras EPSON via IP e Porta... 
                // "M10" - quando está utilizando o M10 da Elgin
                params += ";@DISPOSITIVO(NAME="+initConfig.getString("dispositivoName")+")";
            }
            dmf = DarumaMobile.inicializar(context, params);
        }catch (Exception e){
            strAux= e.getMessage();
            mensagem("Erro na rotina de configuração: "+ strAux);
        }
    }

    /**
     * 
     * IMPRESSORA
     * 
     */

    @ReactMethod
    public void imprimir() {
        
    }

    /**
     * 
     * NFCe
     * 
     */
    
    // CONFIGURAÇÃO DE NFCE E EQUIPAMENTO QUE É CHAMADO PELA THREAD ACIMA.
    // PARA ALGUNS DETALHES DE CONFIGURAÇÃO CONSULTE
    // https://itfast.com.br/site/help/#t=Android%2FNFCE%2FConfiguracoes_Iniciais_NFCe.htm&rhsearch=RegAlterarValor_NFCe&rhsyns=%20
    void configGneToNfce(HashMap config) {
        Log.i("Teste", "Dentro da conf");

        HashMap<String, String> ide = (HashMap<String, String>) config.get("ide");

        //PREENCHA AS CHAVES COM OS SEUS DADOS JUNTO DA MIGRATE PARA A EMISSÃO
        dmf.RegAlterarValor_NFCe("IDE\\cUF", ide.get("cUF"), false);
        dmf.RegAlterarValor_NFCe("IDE\\cMunFG", ide.get("cMunFG"), false);

        HashMap<String, String> emit = (HashMap<String, String>) config.get("emit");

        dmf.RegAlterarValor_NFCe("EMIT\\CNPJ", emit.get("CNPJ"), false);
        dmf.RegAlterarValor_NFCe("EMIT\\IE", emit.get("IE"), false);
        dmf.RegAlterarValor_NFCe("EMIT\\xNome", emit.get("xNome"), false);

        HashMap<String, String> enderEmit = (HashMap<String, String>) emit.get("enderEmit");
        dmf.RegAlterarValor_NFCe("EMIT\\ENDEREMIT\\UF", enderEmit.get("UF"), false);

        HashMap<String, String> configuracao = (HashMap<String, String>) config.get("configuracao");

        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\EmpPK", configuracao.get("EmpPK"), false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\EmpCK", configuracao.get("EmpCK"), false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\Token", configuracao.get("Token"), false);


        //OS DADOS ABAIXO PODEM SER MANTIDOS PARA TESTE
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\TipoAmbiente", "2", false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\EmpCO", "001", false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\IdToken", "000001", false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\ArredondarTruncar", "A", false);
        dmf.RegAlterarValor_NFCe("EMIT\\CRT", "3", false);
        // Impressora
        // "TECTOY" para as bobinas de 58mm (bobina POS)
        // "TECTOY_80" para as boninas de 80mm (Bobina de mini impressora)
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\Impressora", configuracao.get("Impressora"), false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\AvisoContingencia", "1", false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\ImpressaoCompleta", "1", false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\NumeracaoAutomatica", "1", false);


        HashMap<String, String> imposto = (HashMap<String, String>) config.get("imposto");
        HashMap<String, String> icms = (HashMap<String, String>) imposto.get("imposto");
        HashMap<String, String> pis = (HashMap<String, String>) imposto.get("imposto");
        HashMap<String, String> cofins = (HashMap<String, String>) imposto.get("imposto");

        dmf.RegAlterarValor_NFCe("IMPOSTO\\ICMS\\ICMS00\\orig", "0", false);
        dmf.RegAlterarValor_NFCe("IMPOSTO\\ICMS\\ICMS00\\CST", "00", false);
        dmf.RegAlterarValor_NFCe("IMPOSTO\\ICMS\\ICMS00\\modBC", "3", false);
        dmf.RegAlterarValor_NFCe("IMPOSTO\\PIS\\PISNT\\CST", "07", false);
        dmf.RegAlterarValor_NFCe("IMPOSTO\\COFINS\\COFINSNT\\CST", "07", false);

        dmf.RegPersistirXML_NFCe();
        dmf.confNumSeriesNF_NFCe("77", "890");
   
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\HabilitarSAT", "0");
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\EstadoCFe", "0");
        Log.i("Teste", "Dentro da conf fim");

    }

    public class NfceThread implements Runnable {
        private ReadableMap config;

        public NfceThread(ReadableMap config) {
            this.config = config;
        }

        public void run() {
            try {
                Looper.prepare();
                configGneToNfce(this.config);
            } catch (Exception de) {
                strAux= de.getMessage();
                Log.i("Teste", "Erro na configuração: "+strAux);
                mensagem("Erro na configuração: "+strAux);
                return;
            }
        }
    }

    @ReactMethod 
    public void configNFCe(ReadableMap configs) {
        //CRIANDO THREAD PARA EXECUÇÃO DAS CONFIGURAÇÃO PERTINENTES A NFCE - O MOTIVO DE SER EM UMA THREAD É PRA NÃO TRAVAR A INTERFACE
        Thread thrCgf;
        try {
            strAux="";
            thrCgf = new Thread(new NfceThread(configs));
            thrCgf.start();
            thrCgf.join();
            Log.i("Teste", "CONFIGURADO");
        } catch (Exception e) {
            strAux= e.getMessage();
            mensagem("Erro na rotina de configuração: "+ strAux);
            return;
        }

    }


//    MÉTODO DE ABERTURA EXTERNALIZADO PARA A CAMADA DO REAT-NATIVE
//    https://itfast.com.br/site/help/#t=NFCE%2FaCFAbrir_NFCe.htm&rhsearch=aCFAbrir_NFCe&rhhlterm=aCFAbrir_NFCe&rhsyns=%20
    @ReactMethod
    public void aCFAbrir_NFCe(String pszCPF, String pszNome, String pszLgr, String pszNro, String pszBairro, String pszcMun, String pszMunicipio, String pszUF, String pszCEP, Promise promise){
        Log.i("Teste",  "aCFAbrir_NFCe: " +pszCPF +", "+ pszNome+", "+ pszLgr+", "+ pszNro+", "+ pszBairro+", "+ pszcMun+", "+ pszMunicipio+", "+ pszUF+", "+ pszCEP);
        try{
            int ret = dmf.aCFAbrir_NFCe(pszCPF, pszNome, pszLgr, pszNro, pszBairro, pszcMun, pszMunicipio, pszUF, pszCEP);
            promise.resolve(ret);
        }catch(Exception e) {
            promise.reject("Create Event Error", e);
        }
    }

    //    MÉTODO DE VENDA DE ITEM EXTERNALIZADO PARA A CAMADA DO REAT-NATIVE
//    https://itfast.com.br/site/help/#t=NFCE%2FaCFVenderCompleto_NFCe.htm&rhsearch=aCFVenderCompleto_NFCE&rhhlterm=aCFVenderCompleto_NFCE&rhsyns=%20

    @ReactMethod
    public void aCFVenderCompleto_NFCE(String pszCargaTributaria, String pszQuantidade, String pszPrecoUnitario, String pszTipoDescAcresc, String pszValorDescAcresc, String pszCodigoItem, String pszNCM, String pszCFOP, String pszUnidadeMedida, String pszDescricaoItem, String pszUsoFuturo, Promise promise){
        Log.i("Teste",  "aCFVenderCompleto_NFCE: " +pszCargaTributaria +", "+ pszQuantidade+", "+ pszPrecoUnitario+", "+ pszTipoDescAcresc+", "+ pszValorDescAcresc+", "+ pszCodigoItem+", "+ pszNCM+", "+ pszCFOP+", "+ pszUnidadeMedida+", "+ pszDescricaoItem+", "+ pszUsoFuturo);
        try{
            promise.resolve(dmf.aCFVenderCompleto_NFCe(pszCargaTributaria, pszQuantidade, pszPrecoUnitario, pszTipoDescAcresc, pszValorDescAcresc, pszCodigoItem, pszNCM, pszCFOP, pszUnidadeMedida, pszDescricaoItem, pszUsoFuturo));
        }catch (Exception e){
            promise.reject("Create Event Error", e);
        }

    }

    //    MÉTODO DE TOTALIZAÇÃO EXTERNALIZADO PARA A CAMADA DO REAT-NATIVE
//    https://itfast.com.br/site/help/#t=NFCE%2FaCFTotalizar_NFCe.htm&rhsearch=aCFTotalizar_NFCe&rhhlterm=aCFTotalizar_NFCe&rhsyns=%20
    @ReactMethod
    public void aCFTotalizar_NFCe(String pszTipoDescAcresc, String pszValorDescAcresc,Promise promise){
        Log.i("Teste",  "aCFTotalizar_NFCe: " +pszTipoDescAcresc +", "+ pszValorDescAcresc);
        try{
            promise.resolve( dmf.aCFTotalizar_NFCe(pszTipoDescAcresc, pszValorDescAcresc));
        }catch (Exception e){
            promise.reject("Create Event Error", e);
        }

    }

    //    MÉTODO DE PAGAMENTO EXTERNALIZADO PARA A CAMADA DO REAT-NATIVE
//    https://itfast.com.br/site/help/#t=NFCE%2FaCFEfetuarPagamento_NFCe.htm&rhsearch=aCFEfetuarPagamento_NFCe&rhhlterm=aCFEfetuarPagamento_NFCe&rhsyns=%20
    @ReactMethod
    public void aCFEfetuarPagamento_NFCe(String pszFormaPgto, String pszValor, Promise promise){
        Log.i("Teste",  "aCFEfetuarPagamento_NFCe: " +pszFormaPgto +", "+ pszValor);
        try{
            promise.resolve( dmf.aCFEfetuarPagamento_NFCe(pszFormaPgto, pszValor));
        }catch (Exception e){
            promise.reject("Create Event Error", e);
        }
    }

    //    MÉTODO DE ENCERRAMENTO EXTERNALIZADO PARA A CAMADA DO REAT-NATIVE
//    IMPORTATE. O MESMO DEVE SER CRIADO DENTRO DE UMA THREAD PARA NÃO QUEBRAR A APLICAÇÃO
//    https://itfast.com.br/site/help/#t=NFCE%2FtCFEncerrar_NFCe.htm&rhsearch=tCFEncerrar_NFCe&rhhlterm=tCFEncerrar_NFCe&rhsyns=%20
    @ReactMethod
    public void tCFEncerrar_NFCe(String strMsgPromocional, Promise promise){
        Log.i("Teste",  "tCFEncerrar_NFCe: " +strMsgPromocional);
        try {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    promise.resolve(dmf.tCFEncerrar_NFCe(strMsgPromocional));
                }
            });
        } catch (Exception e) {
            strAux= e.getMessage();
            mensagem("Ocorreu erro na venda: "+ strAux);
            promise.reject("Create Event Error", e);
            return;
        }
    }

    //    MÉTODO DE CANCELAMENTO EXTERNALIZADO PARA A CAMADA DO REAT-NATIVE
//    IMPORTATE. O MESMO DEVE SER CRIADO DENTRO DE UMA THREAD PARA NÃO QUEBRAR A APLICAÇÃO
//    https://itfast.com.br/site/help/#t=NFCE%2FtCFCancelar_NFCe.htm&rhsearch=tCFCancelar_NFCe&rhhlterm=tCFCancelar_NFCe&rhsyns=%20
    @ReactMethod
    public void tCFCancelar_NFCe(String strNNF, String strNSerie, String strChAcesso, String strProtAutorizacao, String strJustificativa, Promise promise){
        Log.i("Teste",  "tCFCancelar_NFCe: " +strNNF +", "+strNSerie +", "+strChAcesso +", "+strProtAutorizacao +", "+strJustificativa);
        try {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    promise.resolve(dmf.tCFCancelar_NFCe(strNNF, strNSerie, strChAcesso, strProtAutorizacao,strJustificativa));
                }
            });
        } catch (Exception e) {
            strAux= e.getMessage();
            mensagem("Ocorreu erro no cancelamento: "+ strAux);
            promise.reject("Create Event Error", e);
            return;
        }

    }

}
