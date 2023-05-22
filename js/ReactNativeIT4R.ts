import { NativeModules } from 'react-native';
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

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFAbrir_NFCe.htm&rhsearch=aCFAbrir_NFCe&rhhlterm=aCFAbrir_NFCe&rhsyns=%20
    abrirCupom: async () => {
        const ret = await It4rModule.aCFAbrir_NFCe(
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            ''
        );
        if (ret === 1) {
            console.log('SUCESSO ABRIR CUPOM!');
        } else {
            console.log('ERRO ABRIR CUPOM!');
        }
    },

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFVenderCompleto_NFCe.htm&rhsearch=aCFVenderCompleto_NFCE&rhhlterm=aCFVenderCompleto_NFCE&rhsyns=%20
    venderItem: async () => {
        const ret = await It4rModule.aCFVenderCompleto_NFCE(
            '17.50',
            '1.00',
            '8.00',
            'D$',
            '0.00',
            '0001',
            '21050010',
            '5102',
            'UN',
            'GUARANA 2L de teste com txt quebra linha abcd1234567909876543321',
            'CEST=2300100;cEAN=123456789012;cEANTrib=123456789012;'
        );
        if (ret === 1) {
            //ToastAndroid.show('SUCESSO VENDER ITEM!', ToastAndroid.SHORT);
        } else {
            //ToastAndroid.show('ERRO VENDER ITEM!', ToastAndroid.SHORT);
        }
    },

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFTotalizar_NFCe.htm&rhsearch=aCFTotalizar_NFCe&rhhlterm=aCFTotalizar_NFCe&rhsyns=%20
    totalizarCupom: async () => {
        const ret = await It4rModule.aCFTotalizar_NFCe('D$', '0.00');
        if (ret === 1) {
            // ToastAndroid.show('SUCESSO TOTALIZAR CUPOM!', ToastAndroid.SHORT);
        } else {
            // ToastAndroid.show('ERRO TOTALIZAR CUPOM!', ToastAndroid.SHORT);
        }
    },

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFEfetuarPagamento_NFCe.htm&rhsearch=aCFEfetuarPagamento_NFCe&rhhlterm=aCFEfetuarPagamento_NFCe&rhsyns=%20
    pagarCupom: async () => {
        const ret = await It4rModule.aCFEfetuarPagamento_NFCe('Dinheiro', '100.00');
        if (ret === 1) {
            // ToastAndroid.show('SUCESSO PAGAR CUPOM!', ToastAndroid.SHORT);
        } else {
            //ToastAndroid.show('ERRO PAGAR CUPOM!', ToastAndroid.SHORT);
        }
    },

    // https://itfast.com.br/site/help/#t=NFCE%2FtCFEncerrar_NFCe.htm&rhsearch=tCFEncerrar_NFCe&rhhlterm=tCFEncerrar_NFCe&rhsyns=%20
    encerrarCupom: async () => {
        const ret = await It4rModule.tCFEncerrar_NFCe('Obrigado! Volte Sempre!!!');
        if (ret === 0) {
            //ToastAndroid.show('SUCESSO ENCERRAR CUPOM!', ToastAndroid.SHORT);
        } else {
            //ToastAndroid.show('RETORNO DO ENCERRAMENTO' + ret, ToastAndroid.SHORT);
        }
    },

    // https://itfast.com.br/site/help/#t=NFCE%2FtCFCancelar_NFCe.htm&rhsearch=tCFCancelar_NFCe&rhhlterm=tCFCancelar_NFCe&rhsyns=%20
    cancelarCupom: async () => {
        const ret = await It4rModule.tCFCancelar_NFCe('', '', '', '', '');
        if (ret === 0) {
            //ToastAndroid.show('SUCESSO CANCELAR CUPOM!', ToastAndroid.SHORT);
        } else {
            //ToastAndroid.show('RETORNO DO CANCELAR' + ret, ToastAndroid.SHORT);
        }
    },
}


function ReactNativeIT4R() {
    const { It4rModule } = NativeModules;

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFAbrir_NFCe.htm&rhsearch=aCFAbrir_NFCe&rhhlterm=aCFAbrir_NFCe&rhsyns=%20
    const abrirCupom = async () => {
        const ret = await It4rModule.aCFAbrir_NFCe(
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            ''
        );
        if (ret === 1) {
            console.log('SUCESSO ABRIR CUPOM!');
        } else {
            console.log('ERRO ABRIR CUPOM!');
        }
    };

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFVenderCompleto_NFCe.htm&rhsearch=aCFVenderCompleto_NFCE&rhhlterm=aCFVenderCompleto_NFCE&rhsyns=%20
    const venderItem = async () => {
        const ret = await It4rModule.aCFVenderCompleto_NFCE(
            '17.50',
            '1.00',
            '8.00',
            'D$',
            '0.00',
            '0001',
            '21050010',
            '5102',
            'UN',
            'GUARANA 2L de teste com txt quebra linha abcd1234567909876543321',
            'CEST=2300100;cEAN=123456789012;cEANTrib=123456789012;'
        );
        if (ret === 1) {
            //ToastAndroid.show('SUCESSO VENDER ITEM!', ToastAndroid.SHORT);
        } else {
            //ToastAndroid.show('ERRO VENDER ITEM!', ToastAndroid.SHORT);
        }
    };

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFTotalizar_NFCe.htm&rhsearch=aCFTotalizar_NFCe&rhhlterm=aCFTotalizar_NFCe&rhsyns=%20
    const totalizarCupom = async () => {
        const ret = await It4rModule.aCFTotalizar_NFCe('D$', '0.00');
        if (ret === 1) {
            // ToastAndroid.show('SUCESSO TOTALIZAR CUPOM!', ToastAndroid.SHORT);
        } else {
            // ToastAndroid.show('ERRO TOTALIZAR CUPOM!', ToastAndroid.SHORT);
        }
    };

    // https://itfast.com.br/site/help/#t=NFCE%2FaCFEfetuarPagamento_NFCe.htm&rhsearch=aCFEfetuarPagamento_NFCe&rhhlterm=aCFEfetuarPagamento_NFCe&rhsyns=%20
    const pagarCupom = async () => {
        const ret = await It4rModule.aCFEfetuarPagamento_NFCe('Dinheiro', '100.00');
        if (ret === 1) {
            // ToastAndroid.show('SUCESSO PAGAR CUPOM!', ToastAndroid.SHORT);
        } else {
            //ToastAndroid.show('ERRO PAGAR CUPOM!', ToastAndroid.SHORT);
        }
    };

    // https://itfast.com.br/site/help/#t=NFCE%2FtCFEncerrar_NFCe.htm&rhsearch=tCFEncerrar_NFCe&rhhlterm=tCFEncerrar_NFCe&rhsyns=%20
    const encerrarCupom = async () => {
        const ret = await It4rModule.tCFEncerrar_NFCe('Obrigado! Volte Sempre!!!');
        if (ret === 0) {
            //ToastAndroid.show('SUCESSO ENCERRAR CUPOM!', ToastAndroid.SHORT);
        } else {
            //ToastAndroid.show('RETORNO DO ENCERRAMENTO' + ret, ToastAndroid.SHORT);
        }
    };

    // https://itfast.com.br/site/help/#t=NFCE%2FtCFCancelar_NFCe.htm&rhsearch=tCFCancelar_NFCe&rhhlterm=tCFCancelar_NFCe&rhsyns=%20
    const cancelarCupom = async () => {
        const ret = await It4rModule.tCFCancelar_NFCe('', '', '', '', '');
        if (ret === 0) {
            //ToastAndroid.show('SUCESSO CANCELAR CUPOM!', ToastAndroid.SHORT);
        } else {
            //ToastAndroid.show('RETORNO DO CANCELAR' + ret, ToastAndroid.SHORT);
        }
    };
}

// Onde NAME pode ser: V2, V2PRO, K2, K2_MINI, T2_MINI, T2S, D2_MINI, T2S, 
                // "Q4" - para a impressora Q4 da tectoy - via IP e porta
                // "EPSON" - para as impressoras EPSON via IP e Porta...
                // "M10" - quando está utilizando o M10 da Elgin

export type DeviceNamesType = "V2" | "V2PRO" | "K2" | "K2_MINI" | "T2_MINI" | "T2S" | "D2_MINI" | "T2S" | "Q4" | "EPSON" |  "M10" 
const inicializar = (params?: { dispositivoName?: DeviceNamesType  }) => It4rModule.inicializar(params);

export {
    inicializar,
    it4rNFCe
};