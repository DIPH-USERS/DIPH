import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-general-guide',
  templateUrl: './general-guide.component.html',
  styleUrls: ['./general-guide.component.css']
})
export class GeneralGuideComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router) { }

  private fragment: string; 

  ngAfterViewInit(): void {
    try {
      document.querySelector('#' + this.fragment).scrollIntoView();
    } catch (e) { }
  }

  ngOnInit() {

    this.route.fragment.subscribe(fragment => { this.fragment = fragment; });

    $(document).ready(function () {
      var btt = $('.gototop');
      btt.hide();
      btt.on('click', function (e) {
        $('html, body').animate({
          scrollTop: 0
        }, 500);
        e.preventDefault();
      });

      $(window).on('scroll', function () {
        var /*self = $(this),*/
          height = $(window).height(),
          top = $(window).scrollTop();
        if (top > 190) {
          if (!btt.is(':visible')) {
            btt.show();
          }
        }
        else {
          btt.hide();
        }
      });
    });
  }  


  previouspage() {
    this.router.navigate(['dashboard']);
  }  

}
