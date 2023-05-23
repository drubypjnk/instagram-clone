$('#left-navigation').width(200);
document.addEventListener("click", function (event) {
    var myElement = document.getElementById("search-popup");
    console.log('q3');
    var searchNav = document.getElementById("search-item");
    var notiNav = document.getElementById("notification-item");
    var a = event.target;
    // Check if the click event occurred inside or outside of myElement
    if (event.target != myElement && event.target != searchNav && event.notiNav != myElement && !searchNav.contains(event.target) && !notiNav.contains(event.target) && !myElement.contains(event.target)) {
        console.log('q4');
        var $item = $('.popup-left-item');
        if ($item) {
            $item.remove();
            $('#left-navigation').width(200);

            var logo = '';

            logo += `<svg aria-label="Instagram" class="_ab6-" color="rgb(38, 38, 38)" fill="rgb(38, 38, 38)" height="29" role="img" viewBox="32 4 113 32" width="103"><path clip-rule="evenodd" d="M37.82 4.11c-2.32.97-4.86 3.7-5.66 7.13-1.02 4.34 3.21 6.17 3.56 5.57.4-.7-.76-.94-1-3.2-.3-2.9 1.05-6.16 2.75-7.58.32-.27.3.1.3.78l-.06 14.46c0 3.1-.13 4.07-.36 5.04-.23.98-.6 1.64-.33 1.9.32.28 1.68-.4 2.46-1.5a8.13 8.13 0 0 0 1.33-4.58c.07-2.06.06-5.33.07-7.19 0-1.7.03-6.71-.03-9.72-.02-.74-2.07-1.51-3.03-1.1Zm82.13 14.48a9.42 9.42 0 0 1-.88 3.75c-.85 1.72-2.63 2.25-3.39-.22-.4-1.34-.43-3.59-.13-5.47.3-1.9 1.14-3.35 2.53-3.22 1.38.13 2.02 1.9 1.87 5.16ZM96.8 28.57c-.02 2.67-.44 5.01-1.34 5.7-1.29.96-3 .23-2.65-1.72.31-1.72 1.8-3.48 4-5.64l-.01 1.66Zm-.35-10a10.56 10.56 0 0 1-.88 3.77c-.85 1.72-2.64 2.25-3.39-.22-.5-1.69-.38-3.87-.13-5.25.33-1.78 1.12-3.44 2.53-3.44 1.38 0 2.06 1.5 1.87 5.14Zm-13.41-.02a9.54 9.54 0 0 1-.87 3.8c-.88 1.7-2.63 2.24-3.4-.23-.55-1.77-.36-4.2-.13-5.5.34-1.95 1.2-3.32 2.53-3.2 1.38.14 2.04 1.9 1.87 5.13Zm61.45 1.81c-.33 0-.49.35-.61.93-.44 2.02-.9 2.48-1.5 2.48-.66 0-1.26-1-1.42-3-.12-1.58-.1-4.48.06-7.37.03-.59-.14-1.17-1.73-1.75-.68-.25-1.68-.62-2.17.58a29.65 29.65 0 0 0-2.08 7.14c0 .06-.08.07-.1-.06-.07-.87-.26-2.46-.28-5.79 0-.65-.14-1.2-.86-1.65-.47-.3-1.88-.81-2.4-.2-.43.5-.94 1.87-1.47 3.48l-.74 2.2.01-4.88c0-.5-.34-.67-.45-.7a9.54 9.54 0 0 0-1.8-.37c-.48 0-.6.27-.6.67 0 .05-.08 4.65-.08 7.87v.46c-.27 1.48-1.14 3.49-2.09 3.49s-1.4-.84-1.4-4.68c0-2.24.07-3.21.1-4.83.02-.94.06-1.65.06-1.81-.01-.5-.87-.75-1.27-.85-.4-.09-.76-.13-1.03-.11-.4.02-.67.27-.67.62v.55a3.71 3.71 0 0 0-1.83-1.49c-1.44-.43-2.94-.05-4.07 1.53a9.31 9.31 0 0 0-1.66 4.73c-.16 1.5-.1 3.01.17 4.3-.33 1.44-.96 2.04-1.64 2.04-.99 0-1.7-1.62-1.62-4.4.06-1.84.42-3.13.82-4.99.17-.8.04-1.2-.31-1.6-.32-.37-1-.56-1.99-.33-.7.16-1.7.34-2.6.47 0 0 .05-.21.1-.6.23-2.03-1.98-1.87-2.69-1.22-.42.39-.7.84-.82 1.67-.17 1.3.9 1.91.9 1.91a22.22 22.22 0 0 1-3.4 7.23v-.7c-.01-3.36.03-6 .05-6.95.02-.94.06-1.63.06-1.8 0-.36-.22-.5-.66-.67-.4-.16-.86-.26-1.34-.3-.6-.05-.97.27-.96.65v.52a3.7 3.7 0 0 0-1.84-1.49c-1.44-.43-2.94-.05-4.07 1.53a10.1 10.1 0 0 0-1.66 4.72c-.15 1.57-.13 2.9.09 4.04-.23 1.13-.89 2.3-1.63 2.3-.95 0-1.5-.83-1.5-4.67 0-2.24.07-3.21.1-4.83.02-.94.06-1.65.06-1.81 0-.5-.87-.75-1.27-.85-.42-.1-.79-.13-1.06-.1-.37.02-.63.35-.63.6v.56a3.7 3.7 0 0 0-1.84-1.49c-1.44-.43-2.93-.04-4.07 1.53-.75 1.03-1.35 2.17-1.66 4.7a15.8 15.8 0 0 0-.12 2.04c-.3 1.81-1.61 3.9-2.68 3.9-.63 0-1.23-1.21-1.23-3.8 0-3.45.22-8.36.25-8.83l1.62-.03c.68 0 1.29.01 2.19-.04.45-.02.88-1.64.42-1.84-.21-.09-1.7-.17-2.3-.18-.5-.01-1.88-.11-1.88-.11s.13-3.26.16-3.6c.02-.3-.35-.44-.57-.53a7.77 7.77 0 0 0-1.53-.44c-.76-.15-1.1 0-1.17.64-.1.97-.15 3.82-.15 3.82-.56 0-2.47-.11-3.02-.11-.52 0-1.08 2.22-.36 2.25l3.2.09-.03 6.53v.47c-.53 2.73-2.37 4.2-2.37 4.2.4-1.8-.42-3.15-1.87-4.3-.54-.42-1.6-1.22-2.79-2.1 0 0 .69-.68 1.3-2.04.43-.96.45-2.06-.61-2.3-1.75-.41-3.2.87-3.63 2.25a2.61 2.61 0 0 0 .5 2.66l.15.19c-.4.76-.94 1.78-1.4 2.58-1.27 2.2-2.24 3.95-2.97 3.95-.58 0-.57-1.77-.57-3.43 0-1.43.1-3.58.19-5.8.03-.74-.34-1.16-.96-1.54a4.33 4.33 0 0 0-1.64-.69c-.7 0-2.7.1-4.6 5.57-.23.69-.7 1.94-.7 1.94l.04-6.57c0-.16-.08-.3-.27-.4a4.68 4.68 0 0 0-1.93-.54c-.36 0-.54.17-.54.5l-.07 10.3c0 .78.02 1.69.1 2.09.08.4.2.72.36.91.15.2.33.34.62.4.28.06 1.78.25 1.86-.32.1-.69.1-1.43.89-4.2 1.22-4.31 2.82-6.42 3.58-7.16.13-.14.28-.14.27.07l-.22 5.32c-.2 5.37.78 6.36 2.17 6.36 1.07 0 2.58-1.06 4.2-3.74l2.7-4.5 1.58 1.46c1.28 1.2 1.7 2.36 1.42 3.45-.21.83-1.02 1.7-2.44.86-.42-.25-.6-.44-1.01-.71-.23-.15-.57-.2-.78-.04-.53.4-.84.92-1.01 1.55-.17.61.45.94 1.09 1.22.55.25 1.74.47 2.5.5 2.94.1 5.3-1.42 6.94-5.34.3 3.38 1.55 5.3 3.72 5.3 1.45 0 2.91-1.88 3.55-3.72.18.75.45 1.4.8 1.96 1.68 2.65 4.93 2.07 6.56-.18.5-.69.58-.94.58-.94a3.07 3.07 0 0 0 2.94 2.87c1.1 0 2.23-.52 3.03-2.31.09.2.2.38.3.56 1.68 2.65 4.93 2.07 6.56-.18l.2-.28.05 1.4-1.5 1.37c-2.52 2.3-4.44 4.05-4.58 6.09-.18 2.6 1.93 3.56 3.53 3.69a4.5 4.5 0 0 0 4.04-2.11c.78-1.15 1.3-3.63 1.26-6.08l-.06-3.56a28.55 28.55 0 0 0 5.42-9.44s.93.01 1.92-.05c.32-.02.41.04.35.27-.07.28-1.25 4.84-.17 7.88.74 2.08 2.4 2.75 3.4 2.75 1.15 0 2.26-.87 2.85-2.17l.23.42c1.68 2.65 4.92 2.07 6.56-.18.37-.5.58-.94.58-.94.36 2.2 2.07 2.88 3.05 2.88 1.02 0 2-.42 2.78-2.28.03.82.08 1.49.16 1.7.05.13.34.3.56.37.93.34 1.88.18 2.24.11.24-.05.43-.25.46-.75.07-1.33.03-3.56.43-5.21.67-2.79 1.3-3.87 1.6-4.4.17-.3.36-.35.37-.03.01.64.04 2.52.3 5.05.2 1.86.46 2.96.65 3.3.57 1 1.27 1.05 1.83 1.05.36 0 1.12-.1 1.05-.73-.03-.31.02-2.22.7-4.96.43-1.79 1.15-3.4 1.41-4 .1-.21.15-.04.15 0-.06 1.22-.18 5.25.32 7.46.68 2.98 2.65 3.32 3.34 3.32 1.47 0 2.67-1.12 3.07-4.05.1-.7-.05-1.25-.48-1.25Z" fill="currentColor" fill-rule="evenodd"></path></svg>`;
            $('#logo-svg').empty();
            $('#logo-svg').append(logo);
        }
    }
});
function toggleLights(isSearch, type) {
    var $dim = $('#left-navigation');
    var poped = document.getElementById('search-popup');
    var id;
    if (poped) {
        id = $('#search-popup').data("id");
    }

    var width = $dim.width();
    if ((poped != null && type != id && id != null) || poped == null) {
        $dim.width(70);
        $('.popup-left-item').remove();
        var element = '';
        if (isSearch == true) {
            element += `<div class="popup-left-item" id="search-popup" style="transform: translateX(0%); left: 70px; z-index: 10; position: absolute;" data-id="search">
                            <div class="search-pop-up-container">
                                <div style="opacity: 1; width: 100%; flex-direction: column; overflow-y: hidden; display: flex; height: 100%;">
                                    <div style="margin: 8px 0px 8px 0px; padding: 12px 14px 36px 24px;">
                                        <div>
                                            <span class="search-title" style="line-height: 30px; --base-line-clamp-line-height:30px;">Search</span>
                                        </div>
                                    </div>
                                    <div class="search-panel-popup">
                                        <div class="search-input-container">
                                            <div class="_aawf _aawg _aexm _abli" style="width: 100%;">
                                                <input aria-label="Search input" id="search-input" autocapitalize="none" class="search-input" placeholder="Search" type="text" value="">
                                                <div class="_aaw6" role="dialog">
                                                </div>
                                                <div class="_aawn _9-lv" aria-disabled="false" aria-label="Clear the search box" role="button" tabindex="0" style="cursor: pointer;">
                                                    <svg id="icon-loading" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" style="margin: auto; background: rgb(239,239,239); display: none; shape-rendering: auto;" width="20px" height="20px" viewBox="0 0 100 100" preserveAspectRatio="xMidYMid">
                                                        <circle cx="50" cy="50" fill="none" stroke="#000000" stroke-width="10" r="35" stroke-dasharray="164.93361431346415 56.97787143782138">
                                                            <animateTransform attributeName="transform" type="rotate" repeatCount="indefinite" dur="1s" values="0 50 50;360 50 50" keyTimes="0;1"></animateTransform>
                                                        </circle>
                                                    </svg>
                                                    <svg id="icon-clear" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" style="margin: auto; background: rgb(239,239,239); display: block; shape-rendering: auto;" width="20px" height="20px" viewBox="0 0 100 100" preserveAspectRatio="xMidYMid"><path d="M175 175C184.4 165.7 199.6 165.7 208.1 175L255.1 222.1L303 175C312.4 165.7 327.6 165.7 336.1 175C346.3 184.4 346.3 199.6 336.1 208.1L289.9 255.1L336.1 303C346.3 312.4 346.3 327.6 336.1 336.1C327.6 346.3 312.4 346.3 303 336.1L255.1 289.9L208.1 336.1C199.6 346.3 184.4 346.3 175 336.1C165.7 327.6 165.7 312.4 175 303L222.1 255.1L175 208.1C165.7 199.6 165.7 184.4 175 175V175zM512 256C512 397.4 397.4 512 256 512C114.6 512 0 397.4 0 256C0 114.6 114.6 0 256 0C397.4 0 512 114.6 512 256zM256 48C141.1 48 48 141.1 48 256C48 370.9 141.1 464 256 464C370.9 464 464 370.9 464 256C464 141.1 370.9 48 256 48z"/></svg>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="search-result-container" id="search-result-container">
                                            <div class="result-container">
                                                <div class="_abo2 _ag1s">
                                                    <span class="search-title-span" style="line-height: 20px; --base-line-clamp-line-height:20px;">Result</span>
                                                </div>
                                                <div class="searched-result" id="result-row-container">
                                                    <div class="_aacl _aaco _aacw _aacy _aad6 _aadb">No result.</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>`;
        } else {
            element += `<div class="notification-panel popup-left-item" id="search-popup" style="transform: translateX(0%);z-index: 3;" data-id="notify">
                            <div class="">
                                <div class="notification-container">
                                    <div style="opacity: 1;    width: 100%;    height: 100%;">
                                        <div>
                                            <div  style="padding-top: 16px;padding-right: 24px;padding-bottom: 24px;padding-left: 24px;">
                                                <span class="pop-up-title" style="line-height: 30px; --base-line-clamp-line-height:30px;">Notifications</span>
                                            </div>
                                            <div>

                                                <div role="progressbar" aria-label="Loading..." data-visualcompletion="loading-state" class="notification-list-title" style="animation-delay: 0ms;"></div>`;
            for (var i = 0; i < 20; i++) {
                element += `<div class="item-notification-loading">
                                <div role="progressbar" aria-label="Loading..." data-visualcompletion="loading-state" class="item-loading" style="animation-delay: 200ms;    border-radius: 22px;flex-shrink: 0;width: 44px;height: 44px;">
                                </div>
                                <div role="progressbar" aria-label="Loading..." data-visualcompletion="loading-state" class="item-loading" style="animation-delay: 400ms;    border-radius: 4px;    flex-grow: 1;    height: 14px;margin-left: 12px;    width: 100%;">
                                </div>
                                <div role="progressbar" aria-label="Loading..." data-visualcompletion="loading-state" class="item-loading" style="    width: 44px;margin-left: 22px;    height: 44px;flex-shrink: 0;animation-delay: 600ms;    border-radius: 4px; ">
                                </div>
                            </div>`;
            }
            element +=                          `<div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
        }
        var $element = $('#search-popup');
        $('#left-container').append(element);
        $('#icon-clear').click(function (event) {
            $('#search-input').val('');
        });
        $('#search-item').on("change", function (event) {
            console.log('a');
        });
        if (isSearch) {
            var inputElement = document.getElementById("search-input");
            inputElement.addEventListener("keydown", function (event) {
                if (event.keyCode === 13) {
                    // Retrieve the input value
                    var inputValue = inputElement.value;
                    $('#icon-clear').css("display", "none");
                    $('#icon-loading').css("display", "block");
                    // Do something with the input value
                    console.log("Enter key pressed: " + inputValue);
                    searchUser(inputValue);
                }
            });
        }
        var logo = '';
        logo += `<svg aria-label="Instagram" class="x1lliihq x1n2onr6" color="rgb(38, 38, 38)" fill="rgb(38, 38, 38)" height="24" role="img" viewBox="0 0 24 24" width="24"><title>Instagram</title><path d="M12 2.982c2.937 0 3.285.011 4.445.064a6.087 6.087 0 0 1 2.042.379 3.408 3.408 0 0 1 1.265.823 3.408 3.408 0 0 1 .823 1.265 6.087 6.087 0 0 1 .379 2.042c.053 1.16.064 1.508.064 4.445s-.011 3.285-.064 4.445a6.087 6.087 0 0 1-.379 2.042 3.643 3.643 0 0 1-2.088 2.088 6.087 6.087 0 0 1-2.042.379c-1.16.053-1.508.064-4.445.064s-3.285-.011-4.445-.064a6.087 6.087 0 0 1-2.043-.379 3.408 3.408 0 0 1-1.264-.823 3.408 3.408 0 0 1-.823-1.265 6.087 6.087 0 0 1-.379-2.042c-.053-1.16-.064-1.508-.064-4.445s.011-3.285.064-4.445a6.087 6.087 0 0 1 .379-2.042 3.408 3.408 0 0 1 .823-1.265 3.408 3.408 0 0 1 1.265-.823 6.087 6.087 0 0 1 2.042-.379c1.16-.053 1.508-.064 4.445-.064M12 1c-2.987 0-3.362.013-4.535.066a8.074 8.074 0 0 0-2.67.511 5.392 5.392 0 0 0-1.949 1.27 5.392 5.392 0 0 0-1.269 1.948 8.074 8.074 0 0 0-.51 2.67C1.012 8.638 1 9.013 1 12s.013 3.362.066 4.535a8.074 8.074 0 0 0 .511 2.67 5.392 5.392 0 0 0 1.27 1.949 5.392 5.392 0 0 0 1.948 1.269 8.074 8.074 0 0 0 2.67.51C8.638 22.988 9.013 23 12 23s3.362-.013 4.535-.066a8.074 8.074 0 0 0 2.67-.511 5.625 5.625 0 0 0 3.218-3.218 8.074 8.074 0 0 0 .51-2.67C22.988 15.362 23 14.987 23 12s-.013-3.362-.066-4.535a8.074 8.074 0 0 0-.511-2.67 5.392 5.392 0 0 0-1.27-1.949 5.392 5.392 0 0 0-1.948-1.269 8.074 8.074 0 0 0-2.67-.51C15.362 1.012 14.987 1 12 1Zm0 5.351A5.649 5.649 0 1 0 17.649 12 5.649 5.649 0 0 0 12 6.351Zm0 9.316A3.667 3.667 0 1 1 15.667 12 3.667 3.667 0 0 1 12 15.667Zm5.872-10.859a1.32 1.32 0 1 0 1.32 1.32 1.32 1.32 0 0 0-1.32-1.32Z"></path></svg>`
        $('#logo-svg').empty();
        $('#logo-svg').append(logo);

    }
    else {
        //remove
        $('.popup-left-item').remove();
        $dim.width(200);
        var logo = '';

        logo += `<svg aria-label="Instagram" class="_ab6-" color="rgb(38, 38, 38)" fill="rgb(38, 38, 38)" height="29" role="img" viewBox="32 4 113 32" width="103"><path clip-rule="evenodd" d="M37.82 4.11c-2.32.97-4.86 3.7-5.66 7.13-1.02 4.34 3.21 6.17 3.56 5.57.4-.7-.76-.94-1-3.2-.3-2.9 1.05-6.16 2.75-7.58.32-.27.3.1.3.78l-.06 14.46c0 3.1-.13 4.07-.36 5.04-.23.98-.6 1.64-.33 1.9.32.28 1.68-.4 2.46-1.5a8.13 8.13 0 0 0 1.33-4.58c.07-2.06.06-5.33.07-7.19 0-1.7.03-6.71-.03-9.72-.02-.74-2.07-1.51-3.03-1.1Zm82.13 14.48a9.42 9.42 0 0 1-.88 3.75c-.85 1.72-2.63 2.25-3.39-.22-.4-1.34-.43-3.59-.13-5.47.3-1.9 1.14-3.35 2.53-3.22 1.38.13 2.02 1.9 1.87 5.16ZM96.8 28.57c-.02 2.67-.44 5.01-1.34 5.7-1.29.96-3 .23-2.65-1.72.31-1.72 1.8-3.48 4-5.64l-.01 1.66Zm-.35-10a10.56 10.56 0 0 1-.88 3.77c-.85 1.72-2.64 2.25-3.39-.22-.5-1.69-.38-3.87-.13-5.25.33-1.78 1.12-3.44 2.53-3.44 1.38 0 2.06 1.5 1.87 5.14Zm-13.41-.02a9.54 9.54 0 0 1-.87 3.8c-.88 1.7-2.63 2.24-3.4-.23-.55-1.77-.36-4.2-.13-5.5.34-1.95 1.2-3.32 2.53-3.2 1.38.14 2.04 1.9 1.87 5.13Zm61.45 1.81c-.33 0-.49.35-.61.93-.44 2.02-.9 2.48-1.5 2.48-.66 0-1.26-1-1.42-3-.12-1.58-.1-4.48.06-7.37.03-.59-.14-1.17-1.73-1.75-.68-.25-1.68-.62-2.17.58a29.65 29.65 0 0 0-2.08 7.14c0 .06-.08.07-.1-.06-.07-.87-.26-2.46-.28-5.79 0-.65-.14-1.2-.86-1.65-.47-.3-1.88-.81-2.4-.2-.43.5-.94 1.87-1.47 3.48l-.74 2.2.01-4.88c0-.5-.34-.67-.45-.7a9.54 9.54 0 0 0-1.8-.37c-.48 0-.6.27-.6.67 0 .05-.08 4.65-.08 7.87v.46c-.27 1.48-1.14 3.49-2.09 3.49s-1.4-.84-1.4-4.68c0-2.24.07-3.21.1-4.83.02-.94.06-1.65.06-1.81-.01-.5-.87-.75-1.27-.85-.4-.09-.76-.13-1.03-.11-.4.02-.67.27-.67.62v.55a3.71 3.71 0 0 0-1.83-1.49c-1.44-.43-2.94-.05-4.07 1.53a9.31 9.31 0 0 0-1.66 4.73c-.16 1.5-.1 3.01.17 4.3-.33 1.44-.96 2.04-1.64 2.04-.99 0-1.7-1.62-1.62-4.4.06-1.84.42-3.13.82-4.99.17-.8.04-1.2-.31-1.6-.32-.37-1-.56-1.99-.33-.7.16-1.7.34-2.6.47 0 0 .05-.21.1-.6.23-2.03-1.98-1.87-2.69-1.22-.42.39-.7.84-.82 1.67-.17 1.3.9 1.91.9 1.91a22.22 22.22 0 0 1-3.4 7.23v-.7c-.01-3.36.03-6 .05-6.95.02-.94.06-1.63.06-1.8 0-.36-.22-.5-.66-.67-.4-.16-.86-.26-1.34-.3-.6-.05-.97.27-.96.65v.52a3.7 3.7 0 0 0-1.84-1.49c-1.44-.43-2.94-.05-4.07 1.53a10.1 10.1 0 0 0-1.66 4.72c-.15 1.57-.13 2.9.09 4.04-.23 1.13-.89 2.3-1.63 2.3-.95 0-1.5-.83-1.5-4.67 0-2.24.07-3.21.1-4.83.02-.94.06-1.65.06-1.81 0-.5-.87-.75-1.27-.85-.42-.1-.79-.13-1.06-.1-.37.02-.63.35-.63.6v.56a3.7 3.7 0 0 0-1.84-1.49c-1.44-.43-2.93-.04-4.07 1.53-.75 1.03-1.35 2.17-1.66 4.7a15.8 15.8 0 0 0-.12 2.04c-.3 1.81-1.61 3.9-2.68 3.9-.63 0-1.23-1.21-1.23-3.8 0-3.45.22-8.36.25-8.83l1.62-.03c.68 0 1.29.01 2.19-.04.45-.02.88-1.64.42-1.84-.21-.09-1.7-.17-2.3-.18-.5-.01-1.88-.11-1.88-.11s.13-3.26.16-3.6c.02-.3-.35-.44-.57-.53a7.77 7.77 0 0 0-1.53-.44c-.76-.15-1.1 0-1.17.64-.1.97-.15 3.82-.15 3.82-.56 0-2.47-.11-3.02-.11-.52 0-1.08 2.22-.36 2.25l3.2.09-.03 6.53v.47c-.53 2.73-2.37 4.2-2.37 4.2.4-1.8-.42-3.15-1.87-4.3-.54-.42-1.6-1.22-2.79-2.1 0 0 .69-.68 1.3-2.04.43-.96.45-2.06-.61-2.3-1.75-.41-3.2.87-3.63 2.25a2.61 2.61 0 0 0 .5 2.66l.15.19c-.4.76-.94 1.78-1.4 2.58-1.27 2.2-2.24 3.95-2.97 3.95-.58 0-.57-1.77-.57-3.43 0-1.43.1-3.58.19-5.8.03-.74-.34-1.16-.96-1.54a4.33 4.33 0 0 0-1.64-.69c-.7 0-2.7.1-4.6 5.57-.23.69-.7 1.94-.7 1.94l.04-6.57c0-.16-.08-.3-.27-.4a4.68 4.68 0 0 0-1.93-.54c-.36 0-.54.17-.54.5l-.07 10.3c0 .78.02 1.69.1 2.09.08.4.2.72.36.91.15.2.33.34.62.4.28.06 1.78.25 1.86-.32.1-.69.1-1.43.89-4.2 1.22-4.31 2.82-6.42 3.58-7.16.13-.14.28-.14.27.07l-.22 5.32c-.2 5.37.78 6.36 2.17 6.36 1.07 0 2.58-1.06 4.2-3.74l2.7-4.5 1.58 1.46c1.28 1.2 1.7 2.36 1.42 3.45-.21.83-1.02 1.7-2.44.86-.42-.25-.6-.44-1.01-.71-.23-.15-.57-.2-.78-.04-.53.4-.84.92-1.01 1.55-.17.61.45.94 1.09 1.22.55.25 1.74.47 2.5.5 2.94.1 5.3-1.42 6.94-5.34.3 3.38 1.55 5.3 3.72 5.3 1.45 0 2.91-1.88 3.55-3.72.18.75.45 1.4.8 1.96 1.68 2.65 4.93 2.07 6.56-.18.5-.69.58-.94.58-.94a3.07 3.07 0 0 0 2.94 2.87c1.1 0 2.23-.52 3.03-2.31.09.2.2.38.3.56 1.68 2.65 4.93 2.07 6.56-.18l.2-.28.05 1.4-1.5 1.37c-2.52 2.3-4.44 4.05-4.58 6.09-.18 2.6 1.93 3.56 3.53 3.69a4.5 4.5 0 0 0 4.04-2.11c.78-1.15 1.3-3.63 1.26-6.08l-.06-3.56a28.55 28.55 0 0 0 5.42-9.44s.93.01 1.92-.05c.32-.02.41.04.35.27-.07.28-1.25 4.84-.17 7.88.74 2.08 2.4 2.75 3.4 2.75 1.15 0 2.26-.87 2.85-2.17l.23.42c1.68 2.65 4.92 2.07 6.56-.18.37-.5.58-.94.58-.94.36 2.2 2.07 2.88 3.05 2.88 1.02 0 2-.42 2.78-2.28.03.82.08 1.49.16 1.7.05.13.34.3.56.37.93.34 1.88.18 2.24.11.24-.05.43-.25.46-.75.07-1.33.03-3.56.43-5.21.67-2.79 1.3-3.87 1.6-4.4.17-.3.36-.35.37-.03.01.64.04 2.52.3 5.05.2 1.86.46 2.96.65 3.3.57 1 1.27 1.05 1.83 1.05.36 0 1.12-.1 1.05-.73-.03-.31.02-2.22.7-4.96.43-1.79 1.15-3.4 1.41-4 .1-.21.15-.04.15 0-.06 1.22-.18 5.25.32 7.46.68 2.98 2.65 3.32 3.34 3.32 1.47 0 2.67-1.12 3.07-4.05.1-.7-.05-1.25-.48-1.25Z" fill="currentColor" fill-rule="evenodd"></path></svg>`;
        $('#logo-svg').empty();
        $('#logo-svg').append(logo);
    }
}
$('#search-item').click(function (event) {
    $(this).closest('.item-panel').addClass('active');
    toggleLights(true, 'search');
});
$('#notification-item').click(function (event) {
    toggleLights(false, 'notify');
    getNotifications();
});

function searchUser(name) {
    $.ajax({
        type: "GET",
        url: "/Search/" + name,
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            var array = result;
            console.log(array);
            var result = '';
            if (array.length > 0) {
                for (var i = 0; i < array.length; i++) {
                    result += `</div><div role="none" class="quangtv">
                                <a class="result-row" href="/userprofile/${array[i].userId}" role="link" tabindex="0">
                                    <div class="result-row-1">
                                        <div class="result-row-2">
                                            <div class="result-row-3">
                                                <div class="result-row-4">
                                                    <div class="avatar-container">
                                                        <div class="_aarf _aarg" aria-disabled="false" role="button" tabindex="0" style="cursor: pointer;">
                                                            <span  role="link" tabindex="-1" style="width: 44px; height: 44px;">
                                                                <img alt="${array[i].username}'s profile picture" crossorigin="anonymous" draggable="false" src="${array[i].photo.url}">
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="result-infor-container">
                                                    <div class="result-infor-subcontainer1">
                                                        <div class="result-infor-subcontainer2">
                                                            <div class="result-username" style="line-height: 18px; --base-line-clamp-line-height:18px;">
                                                                <span class=>
                                                                    <div class="result-name-child">
                                                                        <span class="" style="line-height: 18px;">${array[i].username}</span>
                                                                    </div>
                                                                </span>
                                                            </div>
                                                            <div class="result-fullname" style="line-height: 18px; --base-line-clamp-line-height:18px;">
                                                                <span>${array[i].fullName}</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>`;
                }
            } else {
                result += `<div class="_aacl _aaco _aacw _aacy _aad6 _aadb">No result.</div>`;
            }

            $('#result-row-container').empty();
            $('#result-row-container').html(result);
            $('#icon-clear').css("display", "block");
            $('#icon-loading').css("display", "none");
        }
    });
}
function getNotifications() {
    $.ajax({
        type: "GET",
        url: "/Notification",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            var array = result;
            
            var result = '';
            result += `<div class="">
                            <div class="notification-container">
                                <div style="opacity: 1;    width: 100%;    height: 100%;">
                                    <div>
                                        <div style="padding-top: 16px;padding-right: 24px;padding-bottom: 24px;padding-left: 24px;">
                                            <span class="pop-up-title" style="line-height: 30px; --base-line-clamp-line-height:30px;">Notifications</span>
                                        </div>`;
            for (var i = 0; i < array.length; i++) {
                result += `                    <div>
                                                <div class="notification-list-title">
                                                    <span class="noti-title" style="line-height: 20px; --base-line-clamp-line-height:20px;">${array[i].title}</span>
                                                </div>
                                                <div>`;
                for (var j = 0; j < array[i].notificationItems.length; j++) {
                    if (array[i].notificationItems[j].type !== 1) {

                        var _targetLink;
                        if (array[i].notificationItems[j].type == 2 || array[i].notificationItems[j].type == 3) {
                            _targetLink = '/Post/' + array[i].notificationItems[j].post.postId;
                        } else if (array[i].notificationItems[j].type == 4) {
                            _targetLink = '/userprofile/' + array[i].notificationItems[j].person.userId;
                        }
                        result += `<div class="notification-item-container" data-pressable-container="true">
                                    <div class="notification-actor-container">
                                        <a class="notification-actor-container-link" href="/userprofile/${array[i].notificationItems[j].person.userId}" role="link" tabindex="0" style="width: 44px; height: 44px;">
                                            <img alt="${array[i].notificationItems[j].person.userId}'s profile picture" class="notification-actor-container-img" crossorigin="anonymous" draggable="false" src="${array[i].notificationItems[j].person.photo.url}">
                                        </a>
                                    </div>
                                    <div class="notification-content-container">
                                        <span class="notification-content-container1" style="line-height: 18px; --base-line-clamp-line-height:18px;">
                                            <a class="notification-target" href="${array[i].notificationItems[j].person.userId}" role="link" tabindex="0">
                                                <span class="notification-actor-name-container" style="line-height: 18px;">
                                                    <div class="notification-actor-name">${array[i].notificationItems[j].person.fullName}</div>
                                                </span>
                                            </a>
                                            <a class="notification-target" href="${_targetLink}">${array[i].notificationItems[j].content} <span class="notification-time" style="line-height: 18px;">${array[i].notificationItems[j].timeAgo}</span></a>
                                        </span>
                                    </div>
                                </div>`;
                    }
                }
                result += `</div>
                    </div>`;
            }
            result += `                </div>
                                    </div>
                                </div>
                        </div>`;

            $('.notification-panel').empty();
            $('.notification-panel').html(result);
        }
    });
}