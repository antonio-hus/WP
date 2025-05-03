$(document).ready(function() {

    // Initial colors for the gradient
    const colors = [
        '#FF6B6B',
        '#4ECDC4',
        '#FFE66D',
        '#6dffb1',
        '#b55cc3',
    ];

    // Initial dragging variables
    let isDragging = false;
    let dragStartX = 0;
    let dragStartY = 0;
    let transitionSpeed = 0.5;
    let colorSplit = 25;

    // Apply initial gradient
    updateGradient($(window).width() / 2, $(window).height() / 2, colorSplit);

    // Track mouse down
    $(document).mousedown(function(e) {
        isDragging = true;
        dragStartX = e.pageX;
        dragStartY = e.pageY;

        // Change cursor to indicate dragging
        $('body').css('cursor', 'grabbing');
    });

    // Track mouse up
    $(document).mouseup(function() {
        isDragging = false;

        // Restore default cursor
        $('body').css('cursor', 'default');
    });

    // Track mouse movement
    $(document).mousemove(function(e) {
        if (isDragging) {
            // Calculate drag distance
            const dragDistanceX = e.pageX - dragStartX;
            const dragDistanceY = e.pageY - dragStartY;
            const dragDistance = Math.sqrt(dragDistanceX * dragDistanceX + dragDistanceY * dragDistanceY);

            // Adjust transition speed based on drag distance
            // Longer drag = slower transition
            transitionSpeed = Math.min(2.0, Math.max(0.1, dragDistance / 10));

            // Adjust color transition ratio based on drag distance
            // Longer drag = change in color distribution
            colorSplit = Math.min(50.0, Math.max(10.0, dragDistance / 10));

            // Update CSS transition property
            $('#gradient-container').css('transition', `background ${transitionSpeed}s ease`);

            // Update gradient with the new split
            updateGradient(e.pageX, e.pageY, colorSplit);
        } else {
            updateGradient(e.pageX, e.pageY);
        }
    });

    function updateGradient(x, y, split = 25) {
        // Calculate percentage positions for the gradient center
        const xPercent = (x / $(window).width() * 100).toFixed(2);
        const yPercent = (y / $(window).height() * 100).toFixed(2);

        // Create the radial-gradient CSS
        const gradientCSS =
            `radial-gradient(circle at ${xPercent}% ${yPercent}%,
        ${colors[0]} 0%, 
        ${colors[1]} ${split}%, 
        ${colors[2]} ${split*2}%,
        ${colors[3]} ${split*3}%,
        ${colors[4]} 100%)`;

        console.log(split);

        // Apply the gradient
        $('#gradient-container').css('background', gradientCSS);
    }
});