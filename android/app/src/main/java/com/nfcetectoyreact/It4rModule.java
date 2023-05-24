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
    ReactApplicationContext context;

    ExecutorService service = Executors.newSingleThreadExecutor();

    void mensagem(String msg){
        Toast.makeText(getReactApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    It4rModule(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    //EXTERNALIZAÇÃO DO NOME DO MÓDULO NATIVO QUE SERÁ CHAMADO NO REACT-NATIVE

    @Override
    public String getName() {
        return "It4rModule";
    }

    @ReactMethod
    public void inicializar(ReadableMap initConfig, Promise promise) {

        // INICIALIZAÇÃO DO DISPOSITIVO. REPARE QUE NO ÚLTIMO PARÂMETRO TEMOS O MODELO DO EQUIPAMENTO
        try{
            String params = "@FRAMEWORK(LOGMEMORIA=200;TRATAEXCECAO=FALSE;TIMEOUTWS=10000;SATNATIVO=FALSE)";
            if(initConfig.hasKey("dispositivoName")) {
                // Onde NAME pode ser: V2, V2PRO, K2, K2_MINI, T2_MINI, T2S, D2_MINI, T2S, 
                // "Q4" - para a impressora Q4 da tectoy - via IP e porta
                // "EPSON" - para as impressoras EPSON via IP e Porta... 
                // "M10" - quando está utilizando o M10 da Elgin
                params += ";@DISPOSITIVO(NAME="+initConfig.getString("dispositivoName")+")";
            }
            dmf = DarumaMobile.inicializar(context, params);
            promise.resolve("Inicializado");
        }catch (Exception e){
            strAux= e.getMessage();
            mensagem("Erro na rotina de configuração: "+ strAux);
            promise.reject("Create Event Error", e);
        }
    }

    /**
     * 
     * IMPRESSORA
     * 
     */

    @ReactMethod
    public void imprimir() {
        try {
            dmf.iniciarComunicacao(); //comando opcional
            dmf.enviarComandoDual("teste de impressão<l></l><b>TESTE de IMPRESSAO</b><l></l>----------------------------------------<l></l><ce>Daruma Mobile</ce><l></l>teste teste teste 1234567890.");
            //dmf.respostaComando(resposta);
            dmf.fecharComunicacao();	//comando opcional			
        } catch (Exception e) {
            strAux= e.getMessage();
            mensagem("Retornou erro: "+ strAux);
        }
    }

    /**
     * 
     * NFCe
     * 
     */
    
    // CONFIGURAÇÃO DE NFCE E EQUIPAMENTO QUE É CHAMADO PELA THREAD ACIMA.
    // PARA ALGUNS DETALHES DE CONFIGURAÇÃO CONSULTE
    // https://itfast.com.br/site/help/#t=Android%2FNFCE%2FConfiguracoes_Iniciais_NFCe.htm&rhsearch=RegAlterarValor_NFCe&rhsyns=%20
    void configGneToNfce(HashMap<String, Object> config) {
        Log.i("Teste", "Dentro da conf");

        HashMap<String, String> ide = (HashMap<String, String>) config.get("ide");

        //PREENCHA AS CHAVES COM OS SEUS DADOS JUNTO DA MIGRATE PARA A EMISSÃO
        dmf.RegAlterarValor_NFCe("IDE\\cUF", ide.get("cUF"), false);
        dmf.RegAlterarValor_NFCe("IDE\\cMunFG", ide.get("cMunFG"), false);

        HashMap<String, String> emit = (HashMap<String, String>) config.get("emit");

        dmf.RegAlterarValor_NFCe("EMIT\\CNPJ", emit.get("CNPJ"), false);
        dmf.RegAlterarValor_NFCe("EMIT\\IE", emit.get("IE"), false);
        dmf.RegAlterarValor_NFCe("EMIT\\xNome", emit.get("xNome"), false);

        HashMap<String, HashMap> emitMap = (HashMap<String, HashMap>) config.get("emit");
        HashMap<String, String> enderEmit = (HashMap<String, String>) emitMap.get("enderEmit");
        dmf.RegAlterarValor_NFCe("EMIT\\ENDEREMIT\\UF", enderEmit.get("UF"), false);

        HashMap<String, String> configuracao = (HashMap<String, String>) config.get("configuracao");

        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\EmpPK", configuracao.get("EmpPK"), false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\EmpCK", configuracao.get("EmpCK"), false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\Token", configuracao.get("Token"), false);


        //OS DADOS ABAIXO PODEM SER MANTIDOS PARA TESTE
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\TipoAmbiente", configuracao.getOrDefault("TipoAmbiente", "2"), false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\EmpCO", configuracao.getOrDefault("EmpCO", "001"), false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\IdToken", configuracao.getOrDefault("IdToken", "000001"), false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\ArredondarTruncar", configuracao.getOrDefault("ArredondarTruncar", "A"), false);
        dmf.RegAlterarValor_NFCe("EMIT\\CRT", emit.getOrDefault("CRT", "3"), false);
        // Impressora
        // "TECTOY" para as bobinas de 58mm (bobina POS)
        // "TECTOY_80" para as boninas de 80mm (Bobina de mini impressora)
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\Impressora", configuracao.getOrDefault("Impressora", "TECTOY_80"), false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\AvisoContingencia", configuracao.getOrDefault("AvisoContingencia", "1"), false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\ImpressaoCompleta", configuracao.getOrDefault("ImpressaoCompleta", "1"), false);
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\NumeracaoAutomatica", configuracao.getOrDefault("NumeracaoAutomatica", "1"), false);

       /**
        icms: {
            // 00: Tributada integralmente
            // 60: Substituição Tributada
            subgrupo: 'ICMS00' | 'ICMS60', // utilizado como imposto padrão para os itens em que não informar o tipo de ICMS antes da venda.
            ICMS00: {
                orig: string,
                CST: '00', // 00 - Tributada integralmente
                modBC: '0',
            },
            ICMS60: {
                orig: string,
                CST: '60', // 60 - ICMS cobrado anteriormente por substituição tributária.
            },
            };
            pis: {
                PISNT_CST: string;
            };
            cofins: {
                COFINSNT_CST: string;
            };
        */
        HashMap<String, HashMap> imposto = (HashMap<String, HashMap>) config.get("imposto");
        HashMap<String, String> icms = (HashMap<String, String>) imposto.get("icms");
        HashMap<String, String> pis = (HashMap<String, String>) imposto.get("pis");
        HashMap<String, String> cofins = (HashMap<String, String>) imposto.get("cofins");

        HashMap<String, HashMap> icmsSub = (HashMap<String, HashMap>) imposto.get("icms");
        if(icms.get("subgrupo") == "ICMS00") {
            HashMap<String, String> icms0 = (HashMap<String, String>) icmsSub.get("ICMS00");
            dmf.RegAlterarValor_NFCe("IMPOSTO\\ICMS\\ICMS00\\orig", icms0.get("orig"), false);
            dmf.RegAlterarValor_NFCe("IMPOSTO\\ICMS\\ICMS00\\CST", icms0.get("CST"), false);
            dmf.RegAlterarValor_NFCe("IMPOSTO\\ICMS\\ICMS00\\modBC", icms0.get("modBC"), false);
        }
        if(icms.get("subgrupo") == "ICMS40") {
            HashMap<String, String> icms4 = (HashMap<String, String>) icmsSub.get("ICMS40");
            dmf.RegAlterarValor_NFCe("IMPOSTO\\ICMS\\ICMS40\\orig", icms4.get("orig"), false);
            dmf.RegAlterarValor_NFCe("IMPOSTO\\ICMS\\ICMS40\\CST", icms4.get("CST"), false);
        }
        if(icms.get("subgrupo") == "ICMS60") {
            HashMap<String, String> icms6 = (HashMap<String, String>) icmsSub.get("ICMS40");
            dmf.RegAlterarValor_NFCe("IMPOSTO\\ICMS\\ICMS60\\orig", icms6.get("orig"), false);
            dmf.RegAlterarValor_NFCe("IMPOSTO\\ICMS\\ICMS60\\CST", icms6.get("CST"), false);
        }


        dmf.RegAlterarValor_NFCe("IMPOSTO\\PIS\\PISNT\\CST", pis.getOrDefault("PISNT_CST","07"), false);
        dmf.RegAlterarValor_NFCe("IMPOSTO\\COFINS\\COFINSNT\\CST", cofins.getOrDefault("COFINSNT_CST","07"), false);

        dmf.RegPersistirXML_NFCe();
        dmf.confNumSeriesNF_NFCe("77", "890");
   
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\HabilitarSAT", configuracao.getOrDefault("HabilitarSAT", "0"));
        dmf.RegAlterarValor_NFCe("CONFIGURACAO\\EstadoCFe", configuracao.getOrDefault("EstadoCFe", "0"));
        Log.i("Teste", "Dentro da conf fim");

    }

    public class NfceThread implements Runnable {
        private HashMap<String, Object> configs;

        public NfceThread(HashMap<String, Object> configs) {
            this.configs = configs;
        }

        public void run() {
            try {
                Looper.prepare();
                configGneToNfce(this.configs);
            } catch (Exception de) {
                strAux= de.getMessage();
                Log.i("Teste", "Erro na configuração: "+strAux);
                mensagem("Erro na configuração: "+strAux);
                return;
            }
        }
    }

    @ReactMethod 
    public void config_NFCe(ReadableMap configs) {
        Log.i("Teste",  "config_NFCe");
        HashMap<String, Object> configsMap = It4rUtils.convertReadableMapToHashMap(configs);
        //CRIANDO THREAD PARA EXECUÇÃO DAS CONFIGURAÇÃO PERTINENTES A NFCE - O MOTIVO DE SER EM UMA THREAD É PRA NÃO TRAVAR A INTERFACE
        Thread thrCgf;
        try {
            strAux="";
            thrCgf = new Thread(new NfceThread(configsMap));
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

    @ReactMethod
    public void aCFConfICMS00_NFCe(String pszOrig, String pszCST, String pszModBC, String pszpICMS, Promise promise){
        Log.i("Teste",  "aCFConfICMS00_NFCe: " +pszOrig +", "+ pszCST+", "+ pszModBC+", "+ pszpICMS);
        try{
            promise.resolve(dmf.aCFConfICMS00_NFCe(pszOrig, pszCST, pszModBC, pszpICMS));
        }catch (Exception e){
            promise.reject("Create Event Error", e);
        }
    }

    @ReactMethod
    public void aCFConfICMS40_NFCe(String pszOrig, String pszCST, String pszvICMSDeson, String pszMotDesICMS, Promise promise){
        Log.i("Teste",  "aCFConfICMS40_NFCe: " +pszOrig +", "+ pszCST+", "+ pszvICMSDeson+", "+ pszMotDesICMS);
        try{
            promise.resolve(dmf.aCFConfICMS40_NFCe(pszOrig, pszCST, pszvICMSDeson, pszMotDesICMS));
        }catch (Exception e){
            promise.reject("Create Event Error", e);
        }
    }

    @ReactMethod
    public void aCFConfICMS60_NFCe(String pszOrig, String pszCST, String pszvBCSTRet, String pszvICMSSTRet, Promise promise){
        Log.i("Teste",  "aCFConfICMS60_NFCe: " +pszOrig +", "+ pszCST+", "+ pszvBCSTRet+", "+ pszvICMSSTRet);
        try{
            promise.resolve(dmf.aCFConfICMS60_NFCe(pszOrig, pszCST, pszvBCSTRet, pszvICMSSTRet));
        }catch (Exception e){
            promise.reject("Create Event Error", e);
        }
    }

    //    MÉTODO DE VENDA DE ITEM EXTERNALIZADO PARA A CAMADA DO REAT-NATIVE
//    https://itfast.com.br/site/help/#t=NFCE%2FaCFVenderCompleto_NFCe.htm&rhsearch=aCFVenderCompleto_NFCE&rhhlterm=aCFVenderCompleto_NFCE&rhsyns=%20
    @ReactMethod
    public void aCFVenderCompleto_NFCe(String pszCargaTributaria, String pszQuantidade, String pszPrecoUnitario, String pszTipoDescAcresc, String pszValorDescAcresc, String pszCodigoItem, String pszNCM, String pszCFOP, String pszUnidadeMedida, String pszDescricaoItem, String pszUsoFuturo, Promise promise){
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
    public void aCFTotalizar_NFCe(String pszTipoDescAcresc, String pszValorDescAcresc, Promise promise){
        Log.i("Teste",  "aCFTotalizar_NFCe: " +pszTipoDescAcresc +", "+ pszValorDescAcresc);
        try{
            promise.resolve(dmf.aCFTotalizar_NFCe(pszTipoDescAcresc, pszValorDescAcresc));
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
