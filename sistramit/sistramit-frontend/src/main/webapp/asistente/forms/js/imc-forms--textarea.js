// appTextareaAmplaria


function limitTextareaChars(textarea, maxLength = 4000, counter = null) {
    
    function getRealLength(text) {
        const singleLineBreaks = (text.match(/(?<!\r)\n/g) || []).length;
        return text.length + singleLineBreaks;
    }
    
    function truncateText(text, maxLen) {
        if (getRealLength(text) <= maxLen) {
            return text;
        }
        
        let truncated = '';
        let realLength = 0;
        
        for (let i = 0; i < text.length; i++) {
            const char = text[i];
            const isLineBreak = char === '\n' && (i === 0 || text[i-1] !== '\r');
            const charWeight = isLineBreak ? 2 : 1;
            
            if (realLength + charWeight > maxLen) {
                break;
            }
            
            truncated += char;
            realLength += charWeight;
        }
        
        return truncated;
    }
    
    function updateCounter(current, max) {
        if (counter) {

			//counter
			//	.innerHTML = txtFormDinTextareaAmplariaMaxima;

			counter
				.querySelector("strong")
					.textContent = `${max}`;

			counter
				.querySelector("span")
					.textContent = `${current}`;

            if (counter.hasAttribute("title")) {

                var title_text = txtFormDinTextareaAmplariaMaximaTitle.replace("%m%", max).replace("%a%", current);

                counter
                    .setAttribute("title", title_text);

            }

			
			// indicacions amb color

			counter
				.classList
					.remove("imc--am-atencio", "imc--am-limit");

			if (current > max * 0.9) {

				counter
					.classList
						.add("imc--am-atencio");

			}
			
			if (current === max) {

				counter
					.classList
						.add("imc--am-limit");

			}

        }
    }
    
    function handleInput(event) {
        const currentText = event.target.value;
        const realLength = getRealLength(currentText);
        
        if (realLength > maxLength) {
            
            const truncatedText = truncateText(currentText, maxLength);
            event.target.value = truncatedText;
            
            
            const cursorPos = Math.min(event.target.selectionStart, truncatedText.length);
            event.target.setSelectionRange(cursorPos, cursorPos);
        }
        
        updateCounter(getRealLength(event.target.value), maxLength);
    }
    
    function handlePaste(event) {
        event.preventDefault();
        
        const pastedText = (event.clipboardData || window.clipboardData).getData('text');
        const currentText = event.target.value;
        const selectionStart = event.target.selectionStart;
        const selectionEnd = event.target.selectionEnd;
        
        
        const newText = currentText.substring(0, selectionStart) + 
                       pastedText + 
                       currentText.substring(selectionEnd);
        
        
        const finalText = truncateText(newText, maxLength);
        event.target.value = finalText;
        
        
        const newCursorPos = Math.min(
            selectionStart + pastedText.length,
            finalText.length
        );
        event.target.setSelectionRange(newCursorPos, newCursorPos);
        
        updateCounter(getRealLength(finalText), maxLength);
        
        event.target.dispatchEvent(new Event('input', { bubbles: true }));
    }
    
    textarea.addEventListener('input', handleInput);
    textarea.addEventListener('paste', handlePaste);

    //alert(textarea.value)
    
    updateCounter(getRealLength(textarea.value), maxLength);
    
    return {
        getCurrentLength: () => getRealLength(textarea.value),
        getMaxLength: () => maxLength,
        getRemainingChars: () => maxLength - getRealLength(textarea.value),
        destroy: () => {
            textarea.removeEventListener('input', handleInput);
            textarea.removeEventListener('paste', handlePaste);
        }
    };
}


$.fn.appTextareaAmplaria = function() {

	this.each(function(){

		var element = $(this)
			,prepara = function() {

				// només TEXTAREA (per ara)

				if (element[0].nodeName !== "TEXTAREA") {
					return;
				}


				// HTML contador

				var contador_html = "<div class=\"imc--textarea-contador\"></div>"
					,pare_ = element.closest(".imc-element")
                    ,etiqueta_ = element.closest(".imc-el-etiqueta");

                if (etiqueta_.length) {

                    // si hi ha etiqueta on posar el contador

                    etiqueta_
						.append( contador_html );

				var contador_ = pare_.find(".imc--textarea-contador")[0]
					,amplaria_max = parseInt( element.attr("data-amplaria"), 10);

                    if (contador_.style.position ===  "absolute") {
                        return;
                    }

                contador_
				    .innerHTML = txtFormDinTextareaAmplariaMaxima;

                let contador_rect = contador_.getBoundingClientRect()
                    ,etiqueta_rect = pare_[0].querySelector("label").getBoundingClientRect();

                if (etiqueta_rect.height > (contador_rect.height + 3) ) {

                    contador_
				        .innerHTML = "<p>(<span></span>/<strong></strong>)</p>";

                    contador_
                        .setAttribute("title", "");

                }


				// activa

				limitTextareaChars(element[0], amplaria_max, contador_);

                } else {

                    // si no hi ha etiqueta, activem i au

                    limitTextareaChars(element[0], amplaria_max);
                    
                }

			};

		// prepara

		prepara();

	});

	return this;
}

// /appTextareaAmplaria
