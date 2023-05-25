import { NativeModules } from 'react-native';
import { ConfigParams } from './models';
const { It4rModule } = NativeModules;

/***
 *  Sequencia da emissao
 *  1. abrirCupom
 *  2. venderItem
 *  3. totalizarCupom
 *  4. pagarCupom
 *  5. encerrarCupom
 *  6. cancelarCupom
 */
const it4rNFCe = {

    config: async (params: ConfigParams) => {
        console.log(params)
        const ret = await It4rModule.config_NFCe(params);
        return ret;
    },

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFAbrir_NFCe.htm&rhsearch=aCFAbrir_NFCe&rhhlterm=aCFAbrir_NFCe&rhsyns=%20
    abrirCupom: async (params: {
        pszCPF: string;
        pszNome: string;
        pszLgr: string;
        pszNro: string;
        pszBairro: string;
        pszcMun: string;
        pszMunicipio: string;
        pszUF: string;
        pszCEP: string;
    }) => {
        // Se algum dos parâmetros for informado, o campo pszCPF é OBRIGATORIO.
        // String pszCPF, String pszNome, String pszLgr, String pszNro, 
        // String pszBairro, String pszcMun, String pszMunicipio, 
        // String pszUF, String pszCEP
        const ret = await It4rModule.aCFAbrir_NFCe(
            params.pszCPF,
            params.pszNome,
            params.pszLgr,
            params.pszNro,
            params.pszBairro,
            params.pszcMun,
            params.pszMunicipio,
            params.pszUF,
            params.pszCEP
        );
        return ret;
    },

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFVenderCompleto_NFCe.htm&rhsearch=aCFVenderCompleto_NFCE&rhhlterm=aCFVenderCompleto_NFCE&rhsyns=%20
    // String pszCargaTributaria, String pszQuantidade, String pszPrecoUnitario, String pszTipoDescAcresc, String pszValorDescAcresc,
    // String pszCodigoItem, String pszNCM, String pszCFOP, String pszUnidadeMedida, String pszDescricaoItem, String pszUsoFuturo
    venderItem: async (params: {
        pszCargaTributaria: string,
        pszQuantidade: string,
        pszPrecoUnitario: string,
        pszTipoDescAcresc: 'D$' | 'A$' | 'D%' | 'A%',
        pszValorDescAcresc: string,
        pszCodigoItem: string,
        pszNCM: string,
        pszCFOP: string,
        pszUnidadeMedida: string,
        pszDescricaoItem: string,
        pszUsoFuturo: string,
        icmsSubgrupo: 'ICMS00' | 'ICMS60' | 'ICMS40'
    }) => {
        if (params.icmsSubgrupo === 'ICMS00') {
            await It4rModule.aCFConfICMS00_NFCe("0", "00", "0", "");
        }
        if (params.icmsSubgrupo === 'ICMS40') {
            await It4rModule.aCFConfICMS40_NFCe("0", "41", "", "9");
        }
        if (params.icmsSubgrupo === 'ICMS60') {
            await It4rModule.aCFConfICMS60_NFCe("0", "60", "", "");
        }
        const ret = await It4rModule.aCFVenderCompleto_NFCe(
            params.pszCargaTributaria, // ex: '17.50',
            params.pszQuantidade, // ex: '1.00',
            params.pszPrecoUnitario, // ex: '8.00',
            params.pszTipoDescAcresc, // ex: 'D$',
            params.pszValorDescAcresc, // ex: '0.00',
            params.pszCodigoItem, // ex: '0001',
            params.pszNCM, // ex: '21050010', Nomenclatura Comum do Mercosul 
            params.pszCFOP, // ex: '5102',
            params.pszUnidadeMedida, // ex: 'UN',
            params.pszDescricaoItem.slice(0, 100), // ex: 'GUARANA 2L de teste com txt quebra linha abcd1234567909876543321',
            params.pszUsoFuturo, // ex: 'CEST=2300100;cEAN=123456789012;cEANTrib=123456789012;',
        );
        return ret
    },

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFTotalizar_NFCe.htm&rhsearch=aCFTotalizar_NFCe&rhhlterm=aCFTotalizar_NFCe&rhsyns=%20
    // String pszTipoDescAcresc, String pszValorDescAcresc
    totalizarCupom: async (params: {
        pszTipoDescAcresc: 'D$' | 'A$' | 'D%' | 'A%';
        pszValorDescAcresc: string;
    }) => {
        const ret = await It4rModule.aCFTotalizar_NFCe(
            params.pszTipoDescAcresc, // ex: 'D$'
            params.pszValorDescAcresc, // ex: '0.00'
        );
        return ret
    },

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFEfetuarPagamento_NFCe.htm&rhsearch=aCFEfetuarPagamento_NFCe&rhhlterm=aCFEfetuarPagamento_NFCe&rhsyns=%20
    // String pszFormaPgto, String pszValor
    pagarCupom: async (params: {
        pszFormaPgto: string,
        pszValor: string;
    }) => {
        const ret = await It4rModule.aCFEfetuarPagamento_NFCe(
            params.pszFormaPgto, // ex: 'Dinheiro',
            params.pszValor, // ex: '100.00'
        );
        return ret
    },

    // https://itfast.com.br/site/help/#t=NFCE%2FtCFEncerrar_NFCe.htm&rhsearch=tCFEncerrar_NFCe&rhhlterm=tCFEncerrar_NFCe&rhsyns=%20
    // String strMsgPromocional
    encerrarCupom: async (params: {
        strMsgPromocional: string;
    }) => {
        const ret = await It4rModule.tCFEncerrar_NFCe(params.strMsgPromocional);
        return ret
    },

    // https://itfast.com.br/site/help/#t=NFCE%2FtCFCancelar_NFCe.htm&rhsearch=tCFCancelar_NFCe&rhhlterm=tCFCancelar_NFCe&rhsyns=%20
    // String strNNF, String strNSerie, String strChAcesso, 
    // String strProtAutorizacao, String strJustificativa
    cancelarCupom: async (params: {
        strNNF: string,
        strNSerie: string,
        strChAcesso: string,
        strProtAutorizacao: string,
        strJustificativa: string,
    }) => {
        const ret = await It4rModule.tCFCancelar_NFCe(
            params.strNNF,
            params.strNSerie,
            params.strChAcesso,
            params.strProtAutorizacao,
            params.strJustificativa
        );
        return ret
    },
}

const it4rPrinter = {
    imprimir: async () => {
        const ret = await It4rModule.imprimir();
        if (ret === 1) {
            console.log('IMPRIMIR: SUCESSO!');
        } else {
            console.log('IMPRIMIR: ERRO!');
        }
    }
}

// Onde NAME pode ser: V2, V2PRO, K2, K2_MINI, T2_MINI, T2S, D2_MINI, T2S, 
// "Q4" - para a impressora Q4 da tectoy - via IP e porta
// "EPSON" - para as impressoras EPSON via IP e Porta...
// "M10" - quando está utilizando o M10 da Elgin

export type DeviceNamesType = "V2" | "V2PRO" | "K2" | "K2_MINI" | "T2_MINI" | "T2S" | "D2S" | "D2_MINI" | "T2S" | "Q4" | "EPSON" | "M10"
const inicializar = (params?: { dispositivoName?: DeviceNamesType }) => It4rModule.inicializar(params);

export {
    inicializar,
    it4rNFCe,
    it4rPrinter
};