import React, {Component} from 'react';
import { ClienteSerive } from './service/ClienteService';
import {InputText} from 'primereact/inputtext';
import {InputMask} from 'primereact/inputmask';

export class CadastroClientes extends Component {

    constructor() {
        super();
        this.state = {
            
        }
        
        this.clienteSerive = new ClienteSerive();
        
    }

    componentDidMount() {
        
    }

    

    render() {

        return (
            
            <div className="p-grid">
                <div className="p-col-12">
                    <div className="card">
                        <h1>Cadastro de clientes</h1>
                        <br/>
                        
                        <div className="card card-w-title">
                        <h1>InputText</h1>
                            <div className="p-grid">
                                <div className="p-col-12 p-md-4">
                                    <InputText placeholder="Name" autoComplete="false" size="50"/>
                                </div>
                                <div className="p-col-12 p-md-3">
                                    <InputMask mask="999-999-999" placeholder="CPF"/>
                                </div>                                
                            </div>
                        </div>    


                    </div>
                </div>
            </div>
        );
    }
}