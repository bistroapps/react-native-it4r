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
        EmpPK: string;
        EmpCK: string;
        Token: string;
        TipoAmbiente: string;
        EmpCO: string;
        IdToken: string;
        ArredondarTruncar: string;
        Impressora: string;
        AvisoContingencia: string;
        ImpressaoCompleta: string;
        NumeracaoAutomatica: string;
        HabilitarSAT: string;
        EstadoCFe: string;
    };
    imposto: {
        icms: {
            ICMS00_orig: string;
            ICMS00_CST: string;
            ICMS00_modBC: string;
        };
        pis: {
            PISNT_CST: string;
        };
        cofins: {
            COFINSNT_CST: string;
        };
    }
}