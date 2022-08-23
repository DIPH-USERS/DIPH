export class District{
    district_id: string;
    district_name: string;
    country_id: string;
    

    constructor(district_id:string, district_name:string, country_id:string) {
        this.district_id = district_id;
        this.district_name = district_name;
        this.country_id = country_id;
      }
}