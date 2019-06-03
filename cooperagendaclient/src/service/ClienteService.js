import axios from 'axios';

export class ClienteSerive {
    
    getClientes() {
        return axios.get('http://localhost:8080/api/clientes');
    }

    deleteCliente(id) {
        return axios.delete(`http://localhost:8080/api/clientes/${id}`);
    }
}