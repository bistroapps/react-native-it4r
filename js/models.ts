export type ConfigParams = {
    ide: {
        cUF: string;
        cMunFG: string;
    };
    emit: {
        CNPJ: string;
        IE: string;
        xNome: string;
        CRT: string;
        enderEmit: {
            UF: string;
        }
    },
    configuracao: {
        EmpPK: string; // Chave do parceiro
        EmpCK: string; // Chave do cliente emitente
        IdToken: string; // Identificação do Token SEFAZ, entregue junto com o token.
        Token: string; // Chave entregue pela SEFAZ, única para cada empresa.
        TipoAmbiente: string;
        EmpCO?: string; // Número do Checkout
        ArredondarTruncar?: 'A' | 'T';
        Impressora: string;
        AvisoContingencia?: string;
        ImpressaoCompleta?: string;
        NumeracaoAutomatica?: string;
        HabilitarSAT?: string;
        EstadoCFe?: string;
    };
    imposto: {
        icms: {
            // 00: Tributada integralmente
            // 60: Substituição Tributada
            subgrupo: 'ICMS00' | 'ICMS60', // utilizado como imposto padrão para os itens em que não informar o tipo de ICMS antes da venda.
            ICMS00: {
                orig: string,
                CST: '00', // 00 - Tributada integralmente
                /**
                    0 - Margem Valor Agregado (%);
                    1 - Pauta (Valor);
                    2 - Preço Tabelado Máx. (valor);
                    3 - valor da operação.
                */
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
    }
}