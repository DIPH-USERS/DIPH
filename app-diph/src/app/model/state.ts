export class State{
    cs_id: string;
    country_id: string;    
    state_name: string;
    status: string;

    constructor(cs_id:string, country_id:string, state_name:string, status:string) {
        this.cs_id = cs_id;
        this.country_id = country_id;
        this.state_name = state_name;
        this.status = status;
      }

}