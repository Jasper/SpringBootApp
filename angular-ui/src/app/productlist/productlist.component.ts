import { Component, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map';
import { ActivatedRoute } from "@angular/router";
import 'rxjs/add/operator/switchMap';
@Component({
  selector: 'app-productlist',
  templateUrl: './productlist.component.html',
  styleUrls: ['./productlist.component.css']
})
export class ProductlistComponent implements OnInit {
  private productsUrl = 'http://localhost:8080/products';
  private categoriesUrl = 'http://localhost:8080/categories';

  productData: any = []
  categoriesData: any = []
  id: String

  constructor(private http : HttpClient, private route: ActivatedRoute) {
    console.log(this.route.snapshot.paramMap.get('id'));
    this.route.paramMap.subscribe(params => { this.id = params['id']; });
  }

  ngOnInit() : void {
    this.http.get(this.productsUrl).subscribe(data => {
      console.log(data);
      this.productData = data;
    });

    this.http.get(this.categoriesUrl).subscribe(data => {
      console.log(data);
      this.categoriesData = data;
    });

    console.log('ID parameter: ' + this.id);
  }

}