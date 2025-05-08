export interface Tarefa {
    id?: string;
    nome: string;
    descricao?: string;
    prioridade: 'BAIXA' | 'MEDIA' | 'ALTA';
    situacao?: 'ABERTA' | 'CONCLUIDA' | 'PENDENTE';
    dataPrevistaConclusao: Date;
    dataCriacao?: Date;
  }