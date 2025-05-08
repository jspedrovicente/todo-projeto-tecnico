import { Component, OnInit } from '@angular/core';
import { formatDate } from '@angular/common';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { CalendarModule } from 'primeng/calendar';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { Textarea } from 'primeng/inputtextarea';
import { ButtonModule } from 'primeng/button';
import { Prioridade } from '../../models/prioridade.model';
import { SelectModule } from 'primeng/select';
import { DatePickerModule } from 'primeng/datepicker';
import { TarefaService } from '../../services/tarefa.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificacaoService } from '../../services/notificacao.service';

@Component({
  selector: 'app-form-tarefa',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, CalendarModule, InputTextModule, DropdownModule, Textarea, ButtonModule, SelectModule, DatePickerModule],
  templateUrl: './form-tarefa.component.html',
  styleUrl: './form-tarefa.component.css'
})
export class FormTarefaComponent implements OnInit {

  prioridades: Prioridade[] = [
    { nome: 'Baixa', valor: 'BAIXA' },
    { nome: 'Média', valor: 'MEDIA' },
    { nome: 'Alta', valor: 'ALTA' }
  ];

  tarefaId: string | null = null;

  tarefaForm!: FormGroup;

  constructor(private fb: FormBuilder,
    private router: Router,
    private tarefaService: TarefaService,
    private notificacaoService: NotificacaoService,
    private route: ActivatedRoute
  ) {}


  ngOnInit() {
    this.tarefaForm = this.fb.group({
      nome: ['', Validators.required],
      prioridade: ['BAIXA', Validators.required],
      dataPrevistaConclusao: [new Date(), [Validators.required, this.dataMinimaValidator]],
      descricao: [''],
      situacao: [{ value: 'ABERTA', disabled: true }]
    });

    this.tarefaId = this.route.snapshot.paramMap.get('id')!;

    if(this.tarefaId){
      this.tarefaService.buscarPorId(this.tarefaId).subscribe(tarefa => {
        if(tarefa.dataPrevistaConclusao){
          tarefa.dataPrevistaConclusao = new Date(tarefa.dataPrevistaConclusao)
        }
        this.tarefaForm.patchValue(tarefa);
      })
    }

  }

  dataMinimaValidator(control: any) {
    const inputDate = new Date(control.value);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return inputDate >= today ? null : { dataInvalida: true };
  }

  onSubmit() {
    if (this.tarefaForm.valid) {
      const tarefa = this.tarefaForm.getRawValue();

      if(this.tarefaId){

        this.tarefaService.editar(this.tarefaId, tarefa).subscribe({
          next: (res) => {
          this.notificacaoService.success('Tarefa atualizada com sucesso.');
          setTimeout(() => {
            this.router.navigate(['']);
          }, 1000);
          }, error: error => {
            this.notificacaoService.error('Erro ao atualizar a Tarefa');
          }
        })

      } else{
        this.tarefaService.criar(tarefa).subscribe({
          next: (res) => {
            console.log('Tarefa criada com sucesso:', res);
            this.notificacaoService.success('Tarefa criada com sucesso.');
            setTimeout(() => {
              this.router.navigate(['']);
            }, 1000);
          }, error: error => {
            this.notificacaoService.error('Erro ao criar a Tarefa');
  
          }
        })
        
      }
     
    } else {
      this.notificacaoService.error('Tarefa não criada, confira os campos novamente')
      this.tarefaForm.markAllAsTouched();
    }
  }
  
}
