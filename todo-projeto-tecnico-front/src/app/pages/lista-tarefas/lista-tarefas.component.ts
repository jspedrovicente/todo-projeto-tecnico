import { Component } from '@angular/core';
import { Tarefa } from '../../models/tarefa.model';
import { TarefaService } from '../../services/tarefa.service';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { DatePipe } from '@angular/common';
import { CommonModule } from '@angular/common';
import { BadgeModule } from 'primeng/badge';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { Prioridade } from '../../models/prioridade.model';
import { Situacao } from '../../models/situacao.model';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { Router } from '@angular/router';
import { NotificacaoService } from '../../services/notificacao.service';

@Component({
  selector: 'app-lista-tarefas',
  imports: [TableModule, BadgeModule, CommonModule, InputTextModule, FormsModule, SelectModule, ButtonModule],
  templateUrl: './lista-tarefas.component.html',
  standalone: true,
  styleUrl: './lista-tarefas.component.css',
  providers: [DatePipe]
})
export class ListaTarefasComponent {
  tarefas: Tarefa[] = [];
  totalRecords: number = 0;
  prioridade: Prioridade[] | undefined;
  situacao: Situacao[] | undefined;


  filtroNome: string = '';
  filtroPrioridade: string = '';
  filtroSituacao: string = '';
  filtroOrdem: string = 'nome';
  filtroTipoOrdem: string = 'DESC';
  filtroPagina: number = 0;

  constructor(private tarefaService: TarefaService,
    private datePipe: DatePipe,
    private router: Router,
    private notificacaoService: NotificacaoService
  ) {}

  ngOnInit(): void {
    this.tarefaService.listar().subscribe((data) => {
      this.tarefas = data.content;
      this.totalRecords = data.totalElements;
      console.log(data.totalElements)
    });

    this.prioridade = [
      { nome: 'Filtrar por Prioridade', valor: ''},
      { nome: 'Baixa', valor: 'BAIXA'},
      { nome: 'Media', valor: 'MEDIA'},
      { nome: 'Alta', valor: 'ALTA'}
    ]

    this.situacao = [
      { nome: 'Filtrar por Situação', valor: ''},
      { nome: 'Aberta', valor: 'ABERTA'},
      { nome: 'Pendente', valor: 'PENDENTE'},
      { nome: 'Concluida', valor: 'CONCLUIDA'},
    ]
  }

  carregarTarefas(event: TableLazyLoadEvent){
    const pagina = event.first! / event.rows!;
    const tamanho = event.rows!;
    const campoOrdem = Array.isArray(event.sortField) ? event.sortField[0] : event.sortField ?? 'nome';
    const tipoOrdem = event.sortOrder === 1 ? 'ASC' : 'DESC';
    this.filtroOrdem = campoOrdem;
    this.filtroTipoOrdem = tipoOrdem;
    this.filtroPagina = pagina;
    this.tarefaService.listar(this.filtroNome, this.filtroPrioridade, this.filtroSituacao, pagina, tamanho, campoOrdem, tipoOrdem).subscribe((data) => {
      this.tarefas = data.content;
      this.totalRecords = data.totalElements;
      console.log(data.totalElements)
    });
    
  }

  aplicarFiltros() {
    this.tarefaService.listar(this.filtroNome, this.filtroPrioridade, this.filtroSituacao, this.filtroPagina, 10, this.filtroOrdem, this.filtroTipoOrdem).subscribe((data) => {
      this.tarefas = data.content;
      this.totalRecords = data.totalElements;
      console.log(data.totalElements)
    });
  }

  novaTarefa() {
    this.router.navigate(['/nova']);
  }

  editarTarefa(tarefaId: string) {
    this.router.navigate(['/editar', tarefaId]);
  }
  
  concluirTarefa(tarefaId: string) {
    this.tarefaService.concluir(tarefaId).subscribe({
      next: (res) => {
        this.notificacaoService.success('Tarefa concluída com sucesso!');
        this.aplicarFiltros()
      },
      error: (error) => {
        this.notificacaoService.error('Erro ao concluir a tarefa.');
      }
    });
  }

  pendenteTarefa(tarefaId: string) {
    this.tarefaService.pendente(tarefaId).subscribe({
      next: (res) => {
        this.notificacaoService.success('Tarefa ajustada com sucesso!');
        this.aplicarFiltros()
      },
      error: (error) => {
        this.notificacaoService.error('Erro ao concluir a tarefa.');
      }
    });
  }
  
  excluirTarefa(tarefaId: string) {
    this.tarefaService.excluir(tarefaId).subscribe({
      next: (res) => {
        this.notificacaoService.success('Tarefa excluída com sucesso!');
        this.aplicarFiltros()
      },
      error: (error) => {
        this.notificacaoService.error('Erro ao excluir a tarefa.');
      }
    })
  };


  prioridadeSeverity(tarefa: Tarefa){
    console.log(tarefa.prioridade)
    if(tarefa.prioridade === "ALTA") return 'danger';
    else if (tarefa.prioridade === 'MEDIA') return 'warn';
    else return 'success'
  }
}
