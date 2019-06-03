import React, {Component} from 'react';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column'

import { ClienteSerive } from './service/ClienteService';
import {Button} from 'primereact/button';
import {Link} from 'react-router-dom'

export class Clientes extends Component {

    constructor() {
        super();
        this.state = {
            dataTableValue:[]
        }


        this.clienteSerive = new ClienteSerive();
        this.actionTemplate = this.actionTemplate.bind(this);
    }

    componentDidMount() {
        this.listarClientes();
    }

    listarClientes = () => {
        this.clienteSerive.getClientes().then(reponse => {
            this.setState({dataTableValue: reponse.data});
        });
    }

    actionTemplate(cliente, column) {
        
        return <div>
            <Button type="button" icon="pi pi-pencil" className="p-button-info" style={{marginRight: '.5em'}} onClick={() => this.editar(cliente)}/>
            <Button type="button" icon="pi pi-trash" className="p-button-info"  onClick={() => this.exluir(cliente)} />
        </div>;
    }

    editar = (cliente) => {
        console.log('Editar cliente: ', cliente.id);        
    }
    
    exluir = (cliente) => {
        console.log('Excluir cliente: ', cliente.id);
        this.clienteSerive.deleteCliente(cliente.id)
            .then(() => this.listarClientes());
            
    }

    render() {

        let footer = <div className="p-clearfix" style={{width:'100%'}}>
            <Link to="/clientes/cadastro"><Button style={{float:'left'}} label="Add" icon="pi pi-plus"/></Link>
        </div>;


        return (
            
            <div className="p-grid">
                <div className="p-col-12">
                    <div className="card">
                        <h1>Clientes</h1>
                        <br/>
                        
                        <div className="p-col-12">
                    <div className="card card-w-title">                            
                            <DataTable value={this.state.dataTableValue} selectionMode="single" paginator={true} rows={10} footer={footer}
                                responsive={true} selection={this.state.dataTableSelection} onSelectionChange={event => this.setState({dataTableSelection: event.value})}>
                                <Column field="nome" header="Nome"/>
                                <Column field="cpf" header="Cpf"/>
                                <Column header="Ações" body={this.actionTemplate.bind(this)} style={{textAlign:'center', width: '8em'}}/>
                            </DataTable>
                        </div>
                    </div>

                    </div>
                </div>
            </div>
        );
    }
}