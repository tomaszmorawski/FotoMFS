var rotate = function(target) {
    var context = target.getContext('2d');
    var text = 'watermark.js';
    var metrics = context.measureText(text);
    var x = (target.width / 2) - (metrics.width + 24);
    var y = (target.height / 2) + 48 * 2;

    context.translate(x, y);
    context.globalAlpha = 0.5;
    context.fillStyle = '#fff';
    context.font = '48px Josefin Slab';
    context.rotate(-45 * Math.PI / 180);
    context.fillText(text, 0, 0);
    return target;
};

watermark(['upload/**'])
    .image(rotate)
    .then(function (img) {
        document.getElementById('rotate').appendChild(img);
    });