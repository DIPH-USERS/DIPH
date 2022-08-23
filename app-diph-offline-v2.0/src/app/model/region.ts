export class Region {
    region_id: string;
    country_id: string;
    region_name: string;
    status: string;
    reproductive_age_women_cfactor:string;
    under_five_children_cfactor:string;

    constructor(region_id: string, country_id: string, region_name: string, status: string, reproductive_age_women_cfactor:string, under_five_children_cfactor:string) {
        this.region_id = region_id;
        this.country_id = country_id;
        this.region_name = region_name;
        this.status = status;
        this.reproductive_age_women_cfactor = reproductive_age_women_cfactor;
        this.under_five_children_cfactor = under_five_children_cfactor;
    }

}