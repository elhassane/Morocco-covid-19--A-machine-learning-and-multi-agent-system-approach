// // Other important pens.
// // Map: https://codepen.io/themustafaomar/pen/ZEGJeZq
// // Navbar: https://codepen.io/themustafaomar/pen/VKbQyZ

// $(function () {

//   'use strict';

//   var aside = $('.side-nav'),
//       showAsideBtn = $('.show-side-btn'),
//       contents = $('#contents'),
//       _window = $(window)

//   showAsideBtn.on("click", function () {
//     $("#" + $(this).data('show')).toggleClass('show-side-nav');
//     contents.toggleClass('margin');
//   });

//   if (_window.width() <= 767) {
//     aside.addClass('show-side-nav');
//   }

//   _window.on('resize', function () {
//     if ($(window).width() > 767) {
//       aside.removeClass('show-side-nav');
//     }
//   });

//   // dropdown menu in the side nav
//   var slideNavDropdown = $('.side-nav-dropdown');

//   $('.side-nav .categories li').on('click', function () {

//     var $this = $(this)

//     $this.toggleClass('opend').siblings().removeClass('opend');

//     if ($this.hasClass('opend')) {
//       $this.find('.side-nav-dropdown').slideToggle('fast');
//       $this.siblings().find('.side-nav-dropdown').slideUp('fast');
//     } else {
//       $this.find('.side-nav-dropdown').slideUp('fast');
//     }
//   });

//   $('.side-nav .close-aside').on('click', function () {
//     $('#' + $(this).data('close')).addClass('show-side-nav');
//     contents.removeClass('margin');
//   });



// });