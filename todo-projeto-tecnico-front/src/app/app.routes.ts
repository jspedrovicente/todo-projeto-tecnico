import { Routes } from '@angular/router';
import { FormTarefaComponent } from './pages/form-tarefa/form-tarefa.component';
import { ListaTarefasComponent } from './pages/lista-tarefas/lista-tarefas.component';

export const routes: Routes = [
    { path: '', component: ListaTarefasComponent },
    { path: 'nova', component: FormTarefaComponent },
    { path: 'editar/:id', component: FormTarefaComponent },
];
