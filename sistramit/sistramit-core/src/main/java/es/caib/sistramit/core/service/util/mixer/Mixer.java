package es.caib.sistramit.core.service.util.mixer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Helper class for operating on audio .
 *
 */
public class Mixer {

	/**
	 * Concatena una lista de audios en formato wav
	 *
	 * @param audioInputStreams
	 *                              Lista de audios a concatenar
	 * @return Audio concatenado
	 * @throws IOException
	 *                                           Error al abrir los ficheros de
	 *                                           audio
	 * @throws UnsupportedAudioFileException
	 */
	public final static byte[] mergeAudio(final List<AudioInputStream> audioInputStreams,
			final AudioInputStream audioNoiseInputStream) throws IOException, UnsupportedAudioFileException {
		byte[] response;
		AudioInputStream appendedFiles = null;

		if (audioInputStreams.size() == 0) {
			return null;
		}
		if (audioInputStreams.size() == 1) {
			return null;
		}

		for (int i = 0; i < audioInputStreams.size() - 1; i++) {
			if (i == 0) {
				appendedFiles = new AudioInputStream(
						new SequenceInputStream(audioInputStreams.get(i), audioInputStreams.get(i + 1)),
						audioInputStreams.get(i).getFormat(),
						audioInputStreams.get(i).getFrameLength() + audioInputStreams.get(i + 1).getFrameLength());
			} else {
				appendedFiles = new AudioInputStream(
						new SequenceInputStream(appendedFiles, audioInputStreams.get(i + 1)), appendedFiles.getFormat(),
						appendedFiles.getFrameLength() + audioInputStreams.get(i + 1).getFrameLength());
			}
		}

		byte[] responsePlain = null;
		try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
			AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, byteOut);
			responsePlain = byteOut.toByteArray();
		}

		if (audioNoiseInputStream != null) {
			final AudioInputStream clipPlain = AudioSystem.getAudioInputStream(new ByteArrayInputStream(responsePlain));

			final Sample s1 = new Sample(clipPlain);
			final Sample s2 = new Sample(audioNoiseInputStream);
			final Sample s3 = Mixer.mix(s1, 1.0D, s2, 0.2D);

			try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
				AudioSystem.write(s3.getAudioInputStream(), AudioFileFormat.Type.WAVE, byteOut);
				response = byteOut.toByteArray();
			}
		} else {
			response = responsePlain;
		}

		return response;

	}

	private final static Sample append(final List<Sample> samples) {
		if (samples.size() == 0) {
			return buildSample(0, new double[0]);
		}

		int sampleCount = 0;

		// append voices to each other
		final double[] first = samples.get(0).getInterleavedSamples();
		sampleCount += samples.get(0).getSampleCount();

		final double[][] samples_ary = new double[samples.size() - 1][];
		for (int i = 0; i < samples_ary.length; i++) {
			samples_ary[i] = samples.get(i + 1).getInterleavedSamples();
			sampleCount += samples.get(i + 1).getSampleCount();
		}

		final double[] appended = concatAll(first, samples_ary);

		return buildSample(sampleCount, appended);
	}

	public final static Sample mix(final Sample sample1, final double volAdj1, final Sample sample2,
			final double volAdj2) {
		final double[] s1_ary = sample1.getInterleavedSamples();
		final double[] s2_ary = sample2.getInterleavedSamples();

		//
		final double[] mixed = mix(s1_ary, volAdj1, s2_ary, volAdj2);

		return buildSample(sample1.getSampleCount(), mixed);
	}

	private static final double[] concatAll(final double[] first, final double[]... rest) {
		int totalLength = first.length;
		for (final double[] array : rest) {
			totalLength += array.length;
		}
		final double[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (final double[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	private static final double[] mix(final double[] sample1, final double volAdj1, final double[] sample2,
			final double volAdj2) {
		for (int i = 0; i < sample1.length; i++) {
			if (i >= sample2.length) {
				sample1[i] = 0;
				break;
			}
			sample1[i] = (sample1[i] * volAdj1) + (sample2[i] * volAdj2);
		}
		return sample1;
	}

	private static final AudioInputStream buildStream(final long sampleCount, final double[] sample) {
		final byte[] buffer = Sample.asByteArray(sampleCount, sample);
		final InputStream bais = new ByteArrayInputStream(buffer);
		return new AudioInputStream(bais, Sample.SC_AUDIO_FORMAT, sampleCount);
	}

	private static final Sample buildSample(final long sampleCount, final double[] sample) {
		final AudioInputStream ais = buildStream(sampleCount, sample);
		return new Sample(ais);
	}
}